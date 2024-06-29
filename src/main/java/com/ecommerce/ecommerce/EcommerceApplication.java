package com.ecommerce.ecommerce;

import com.ecommerce.ecommerce.model.RoleEnum;
import com.ecommerce.ecommerce.model.RolesApp;
import com.ecommerce.ecommerce.repository.RolesRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Role;

import java.util.ArrayList;
import java.util.Arrays;

@SpringBootApplication
public class EcommerceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcommerceApplication.class, args);
	}
}
