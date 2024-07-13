package com.ecommerce.ecommerce.Controller;

import com.ecommerce.ecommerce.Service.ProductCreateForUserService;
import com.ecommerce.ecommerce.model.ProductCreateForUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductCreateForUserController {

    @Autowired
    private ProductCreateForUserService productCreateForUserService;
    @PostMapping("/create")
    public ProductCreateForUser createProduct(@RequestBody ProductCreateForUser createProduct){
        return this.productCreateForUserService.createProduct(createProduct);
    }
}
