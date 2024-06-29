package com.ecommerce.ecommerce.repository;

import com.ecommerce.ecommerce.model.RolesApp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RolesRepository extends JpaRepository<RolesApp, Integer> {

    List<RolesApp> findRoleAppByRoleEnumIn(List<String> roleName);

}
