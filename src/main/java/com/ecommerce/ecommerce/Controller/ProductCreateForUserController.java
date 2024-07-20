package com.ecommerce.ecommerce.Controller;

import com.ecommerce.ecommerce.Service.ProductCreateForUserService;
import com.ecommerce.ecommerce.model.ProductCreateForUser;
import com.ecommerce.ecommerce.repository.UsuarioApprRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductCreateForUserController {

    @Autowired
    private ProductCreateForUserService productCreateForUserService;

    @Autowired
    private UsuarioApprRepository usuarioApprRepository;

    @GetMapping("/getAll")
    public List<ProductCreateForUser> getAllProduct(){
        return productCreateForUserService.getAllProduct();
    }

    @PostMapping("/create")
    public ProductCreateForUser createProduct(@ModelAttribute  ProductCreateForUser createProduct,
                                              @RequestParam("imagenFile")MultipartFile imagenFile,
                                              @RequestParam("userId")String userId ){

        ProductCreateForUser createdProduct = productCreateForUserService.createProduct(createProduct, userId, imagenFile);
        return createdProduct;
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
