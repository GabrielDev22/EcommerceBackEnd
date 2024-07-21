package com.ecommerce.ecommerce.model;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class ProductCreateResponse {

        private Integer id;
        private String productName;
        private String productCategory;
        private Double productPrice;
        private String productSellerName;
        private String productImageUrl;


        public ProductCreateResponse(Integer id, String productName, Character productCategory, int productPrice, String productSellerName, String fileUrl) {
                this.id = id;
                this.productName = productName;
                this.productCategory = String.valueOf(productCategory);
                this.productPrice = Double.valueOf(String.valueOf(productPrice));
                this.productSellerName = productSellerName;
                this.productImageUrl = fileUrl;
        }
}
