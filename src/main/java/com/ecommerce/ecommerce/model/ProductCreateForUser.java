package com.ecommerce.ecommerce.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Arrays;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Accessors(chain = true)
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
    @Column(name = "productImagen")
    private String productImagen;
    private Character productCategory;
    private String productSellerName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private UsuarioApp usuario;

    @Transient
    private UUID userId;

    public boolean isValidProductCategory(Character productCategory){
        return Arrays.asList(CATEGORY_TECNOLOGY,
                CATEGORY_VEHICLES,
                CATEGORY_HOME,
                CATEGORY_FASHION,
                CATEGORY_VIDEO_GAMES,
                CATEGORY_HOME_APPLIANCES).contains(productCategory);
    }

    @Data
    @Accessors(chain = true)
    public static class ProductDTO{
        private Integer id;
        private String productName;
        private String productDescription;
        private int productPrice;
        private String productImagen;
        private Character productCategory;
        private String productSellerName;
        private UUID userId;
    }

    @Data
    @Accessors(chain = true)
    public static class UpdateDTO{
        @NotNull
        @NotEmpty
        private UUID userId;
        private String productName;
        private String productDescription;
        private int productPrice;
        private String productImagen;
        private Character productCategory;
        private String productSellerName;

    }

}
