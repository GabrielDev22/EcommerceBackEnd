package com.ecommerce.ecommerce.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({"username", "message", "roles", "status"})
public class AuthUserResponse {

    private String username;
    private String message;
    private String jwt;
    private  boolean status;

    @Data
    @Accessors(chain = true)
    public static class TokenRefreshRequest{
        private String refreshToken;
    }

}
