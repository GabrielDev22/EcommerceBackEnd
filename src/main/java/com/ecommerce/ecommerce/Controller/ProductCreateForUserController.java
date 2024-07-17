package com.ecommerce.ecommerce.Controller;

import com.ecommerce.ecommerce.Service.ProductCreateForUserService;
import com.ecommerce.ecommerce.model.ProductCreateForUser;
import com.ecommerce.ecommerce.model.UsuarioApp;
import com.ecommerce.ecommerce.repository.UsuarioApprRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

}
