package com.ecommerce.ecommerce.repository;

import com.ecommerce.ecommerce.model.ProductCreateForUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductCreateForUserRepository extends JpaRepository<ProductCreateForUser, Integer> {
}
