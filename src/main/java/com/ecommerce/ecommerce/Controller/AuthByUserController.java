package com.ecommerce.ecommerce.Controller;

import com.ecommerce.ecommerce.Service.UserDetailsServiceImpl;
import com.ecommerce.ecommerce.model.AuthLoginRequest;
import com.ecommerce.ecommerce.model.AuthUserResponse;
import com.ecommerce.ecommerce.model.CreateUser;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthByUserController {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @PostMapping("/create")
    public ResponseEntity<AuthUserResponse> createUser(@RequestBody CreateUser createUser) throws IllegalStateException{
        return new ResponseEntity<>(this.userDetailsService.createUser(createUser), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthUserResponse> login(@RequestBody AuthLoginRequest authLoginRequest){
        return new ResponseEntity<>(this.userDetailsService.loginUser(authLoginRequest), HttpStatus.OK);
    }

    @PostMapping("/refreshToken")
    public ResponseEntity<?> refreshAuthToken(@RequestBody AuthUserResponse.TokenRefreshRequest request){
        try{
            AuthUserResponse response = this.userDetailsService.refreshToken(request);
            return ResponseEntity.ok(response);
        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid refresh token");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<AuthUserResponse> logout(){
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

}
