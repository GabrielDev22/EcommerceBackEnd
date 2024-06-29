package com.ecommerce.ecommerce.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateUser {

    @NotBlank
    private String name;
    @NotBlank
    private String lastName;
    @NotBlank
    private String correo;
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @Valid
    @Size(max = 1, message = "The user cannot have more than 1 roles")
    private List<String> rolesApp;

}
