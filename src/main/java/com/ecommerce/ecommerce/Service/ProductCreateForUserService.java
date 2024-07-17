package com.ecommerce.ecommerce.Service;


import com.ecommerce.ecommerce.model.ProductCreateForUser;
import com.ecommerce.ecommerce.model.UsuarioApp;
import com.ecommerce.ecommerce.repository.ProductCreateForUserRepository;
import com.ecommerce.ecommerce.repository.UsuarioApprRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Service
public class ProductCreateForUserService {

    @Autowired
    private ProductCreateForUserRepository productCreateForUserRepository;

    @Autowired
    private UsuarioApprRepository usuarioApprRepository;


    public List<ProductCreateForUser> getAllProduct(){
        List<ProductCreateForUser> allProducts = null;
        try{
            allProducts = productCreateForUserRepository.findAll();
            if(allProducts.isEmpty()){
                return null;
            }
            return allProducts;
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
                productCreateForUser.setProductImagen(imagenFile.getBytes());
            }
            productCreateForUser.setUsuario(usuarioApp);
            ProductCreateForUser savedProducts = productCreateForUserRepository.save(productCreateForUser);
            return savedProducts;

        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("Error creating product", e);
        }
    }

}
