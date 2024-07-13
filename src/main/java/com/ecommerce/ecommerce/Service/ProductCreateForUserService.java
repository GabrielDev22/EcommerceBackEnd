package com.ecommerce.ecommerce.Service;


import com.ecommerce.ecommerce.model.ProductCreateForUser;
import com.ecommerce.ecommerce.repository.ProductCreateForUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class ProductCreateForUserService {

    @Autowired
    private ProductCreateForUserRepository productCreateForUserRepository;

    public ProductCreateForUser createProduct(ProductCreateForUser productCreateForUser){
        ProductCreateForUser createPosts = new ProductCreateForUser();
        try{
            if(!createPosts.isValidProductCategory(productCreateForUser.getProductCategory())){
                throw new RuntimeException("Invalid field Origin");
            }
            if(!StringUtils.hasText(productCreateForUser.getProductDescription())){
                return createPosts;
            }
            createPosts = productCreateForUserRepository.save(productCreateForUser);
        }catch (Exception e){
            e.printStackTrace();
        }
        return createPosts;
    }

}
