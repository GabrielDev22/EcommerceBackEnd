package com.ecommerce.ecommerce.repository;

import com.ecommerce.ecommerce.model.ProductCreateForUser;
import com.ecommerce.ecommerce.model.UsuarioApp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductCreateForUserRepository extends JpaRepository<ProductCreateForUser, Integer> {

    Optional<ProductCreateForUser> findById(Integer id);
    Optional<ProductCreateForUser> findByUsuario_UserId(UUID userId);
}
