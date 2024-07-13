package com.ecommerce.ecommerce.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Arrays;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "tb_product")
public class ProductCreateForUser implements Serializable {

    public static final char CATEGORY_TECNOLOGY = 'T';
    public static final char CATEGORY_VEHICLES = 'V';
    public static final char CATEGORY_HOME = 'H';
    public static final char CATEGORY_FASHION = 'F';
    public static final char CATEGORY_VIDEO_GAMES = 'G';
    public static final char CATEGORY_HOME_APPLIANCES = 'A';

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String productName;
    private String productDescription;
    private int productPrice;
    private String productImagen;
    private Character productCategory;
    private String productSellerName;

    public boolean isValidProductCategory(Character productCategory){
        return Arrays.asList(CATEGORY_TECNOLOGY,
                CATEGORY_VEHICLES,
                CATEGORY_HOME,
                CATEGORY_FASHION,
                CATEGORY_VIDEO_GAMES,
                CATEGORY_HOME_APPLIANCES).contains(productCategory);
    }

}