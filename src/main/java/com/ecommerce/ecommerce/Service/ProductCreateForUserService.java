package com.ecommerce.ecommerce.Service;


import com.ecommerce.ecommerce.model.ProductCreateForUser;
import com.ecommerce.ecommerce.model.UsuarioApp;
import com.ecommerce.ecommerce.repository.ProductCreateForUserRepository;
import com.ecommerce.ecommerce.repository.UsuarioApprRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.hibernate.PersistentObjectException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ProductCreateForUserService {

    @Autowired
    private FileStorageImagenesService fileStorageImagenesService;

    @Autowired
    private ProductCreateForUserRepository productCreateForUserRepository;

    @Autowired
    private UsuarioApprRepository usuarioApprRepository;


    public List<ProductCreateForUser.ProductDTO> getAllProduct(){
        List<ProductCreateForUser> allProducts = productCreateForUserRepository.findAll();
        List<ProductCreateForUser.ProductDTO> productsDto = new ArrayList();
        try{
            if(allProducts.isEmpty()){
                return null;
            }
            for (ProductCreateForUser products : allProducts){
                ProductCreateForUser.ProductDTO dto = new ProductCreateForUser.ProductDTO();
                dto.setId(products.getId());
                dto.setProductName(products.getProductName());
                dto.setProductDescription(products.getProductDescription());
                dto.setProductPrice(products.getProductPrice());
                dto.setProductImagen(products.getProductImagen());
                dto.setProductCategory(products.getProductCategory());
                dto.setProductSellerName(products.getProductSellerName());
                dto.setUserId(products.getUserId());
                String imagenPath = "/file/images/" + products.getProductImagen();
                dto.setProductImagen(imagenPath);
                productsDto.add(dto);
            }

            return productsDto;

        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("Algo fallo a la hora de listar los productos", e);
        }
    }

    @Transactional
    public ProductCreateForUser createProduct(ProductCreateForUser productCreateForUser, String userId, MultipartFile imagenFile){
        try{
            UsuarioApp usuarioApp = (usuarioApprRepository.findByUserId(UUID.fromString(userId))
                    .orElseThrow(() -> new UsernameNotFoundException("Usuario con UUID " + userId + "no encontrado")));

            if (!productCreateForUser.isValidProductCategory(productCreateForUser.getProductCategory())) {
                throw new RuntimeException("Invalid product category");
            }
            if (productCreateForUser.getProductPrice() <= 0) {
                throw new RuntimeException("Product price must be greater than zero");
            }
            if (productCreateForUser.getProductName() == null || productCreateForUser.getProductName().isEmpty()) {
                throw new RuntimeException("Product name cannot be empty");
            }
            if (productCreateForUser.getProductSellerName() == null || productCreateForUser.getProductSellerName().isEmpty()) {
                throw new RuntimeException("Product seller name cannot be empty");
            }
            if(imagenFile != null && !imagenFile.isEmpty()){
                String filePath = fileStorageImagenesService.storeFile(imagenFile);
                productCreateForUser.setProductImagen(filePath);
            }
            productCreateForUser.setUsuario(usuarioApp);
            ProductCreateForUser savedProducts = productCreateForUserRepository.save(productCreateForUser);
            return savedProducts;

        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("Error creating product", e);
        }
    }

    @Transactional
    public ProductCreateForUser updateProduct(Integer id, ProductCreateForUser.UpdateDTO dto, MultipartFile imagenFile) {
        ProductCreateForUser productUser = productCreateForUserRepository.findById(id)
                .orElseThrow(() -> new NullPointerException("Product con ID " + id + "no encontrado"));

        if (dto.getProductName() == null || dto.getProductDescription() == null || dto.getProductSellerName() == null || dto.getProductPrice() == 0 || dto.getProductCategory() == null) {
            throw new PersistentObjectException("LOS CAMPOS NO PUEDEN SER VACIOS");
        }

        String filePath = null;
        if (imagenFile != null && !imagenFile.isEmpty()) {
            filePath = fileStorageImagenesService.storeFile(imagenFile);
        }

        productUser
                .setProductName(dto.getProductName())
                .setProductPrice(dto.getProductPrice())
                .setProductDescription(dto.getProductDescription())
                .setProductCategory(dto.getProductCategory())
                .setProductSellerName(dto.getProductSellerName())
                .setProductImagen(filePath);
        this.productCreateForUserRepository.save(productUser);
        return productUser;
    }



    public void deleteProductByUserId(String userId, Integer productId){
        try{

            UUID uuidUserId = UUID.fromString(userId);

            UsuarioApp usuarioApp = (usuarioApprRepository.findByUserId(uuidUserId)
                    .orElseThrow(() -> new UsernameNotFoundException("Usuario con UUID " + userId + "no encontrado")));

            ProductCreateForUser product = productCreateForUserRepository.findById(productId)
                    .orElseThrow(() -> new NullPointerException("Product con ID " + productId + "no encontrado"));

            if(!product.getUsuario().getUserId().equals(usuarioApp.getUserId())){
                throw new AccessDeniedException("El usuario no tiene permisos para eliminar este producto");
            }
            productCreateForUserRepository.delete(product);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
