package com.ecommerce.ecommerce.Controller;

import com.ecommerce.ecommerce.Service.ProductCreateForUserService;
import com.ecommerce.ecommerce.model.ProductCreateForUser;
import com.ecommerce.ecommerce.model.ProductCreateResponse;
import com.ecommerce.ecommerce.repository.ProductCreateForUserRepository;
import com.ecommerce.ecommerce.repository.UsuarioApprRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/product")
public class ProductCreateForUserController {

    @Autowired
    private ProductCreateForUserService productCreateForUserService;

    @Autowired
    private ProductCreateForUserRepository productCreateForUserRepository;

    @Autowired
    private UsuarioApprRepository usuarioApprRepository;

    @Value("${file.upload-dir}")
    private String fileName;

    @GetMapping("/getAll")
    public List<ProductCreateForUser.ProductDTO> getAllProduct() {
       return productCreateForUserService.getAllProduct();
    }

    @PostMapping("/create")
    public ProductCreateForUser createProduct(@ModelAttribute  ProductCreateForUser createProduct,
                                              @RequestParam("imagenFile")MultipartFile imagenFile,
                                              @RequestParam("userId")String userId ){

        ProductCreateForUser createdProduct = productCreateForUserService.createProduct(createProduct, userId, imagenFile);
        return createdProduct;
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updateProduct(@PathVariable Integer id,
                                                @ModelAttribute ProductCreateForUser.UpdateDTO dto,
                                                @RequestParam("imagenFile") MultipartFile imagenFile){
        return ResponseEntity.ok(productCreateForUserService.updateProduct(id, dto, imagenFile));
    }

    @DeleteMapping("/delete/{userId}/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable String userId,
                                                @PathVariable Integer productId){
        try {
            productCreateForUserService.deleteProductByUserId(userId, productId);
            return ResponseEntity.ok("Producto eliminado exitosamente");
        }catch (UsernameNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar el producto");
        }
    }

}
