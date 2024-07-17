package com.ecommerce.ecommerce.repository;

import com.ecommerce.ecommerce.model.UsuarioApp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UsuarioApprRepository extends JpaRepository<UsuarioApp, Integer> {

    Optional<UsuarioApp> findByUsername(String username);
    Optional<UsuarioApp> findByUserId(UUID userId);

}
