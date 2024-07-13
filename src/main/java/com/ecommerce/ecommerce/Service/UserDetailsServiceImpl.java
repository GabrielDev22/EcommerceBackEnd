package com.ecommerce.ecommerce.Service;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.ecommerce.ecommerce.model.*;
import com.ecommerce.ecommerce.repository.RolesRepository;
import com.ecommerce.ecommerce.repository.UsuarioApprRepository;
import com.ecommerce.ecommerce.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    private UsuarioApprRepository usuarioApprRepository;


    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        UsuarioApp usuarioApp = usuarioApprRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("El usuario " + username + "no existe"));

        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();

        List<RoleEnum> roleEnumsList = usuarioApp.getRole().stream()
                .map(RolesApp::getRoleEnum)
                .collect(Collectors.toList());

        for(RoleEnum roleEnum : roleEnumsList){
            authorityList.add(new SimpleGrantedAuthority("ROLE_" + roleEnum.name()));
        }

        return new User(usuarioApp.getUsername(),
                usuarioApp.getPassword(),
                usuarioApp.isEnabled(),
                usuarioApp.isAccountNoExpired(),
                usuarioApp.isCredentialNoExpired(),
                usuarioApp.isAccountNoLocked(),
                authorityList);
    }

    public AuthUserResponse loginUser(AuthLoginRequest authLoginRequest){
        String username = authLoginRequest.getUsername();
        String password = authLoginRequest.getPassword();

        Authentication authentication = this.authentication(username, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accessToken = jwtUtils.createToken(authentication);
        boolean loginSuccessful = true;

        AuthUserResponse authUserResponse = new AuthUserResponse(username, "User loged sucessfully", accessToken, loginSuccessful);
        return authUserResponse;
    }

    public AuthUserResponse refreshToken(AuthUserResponse.TokenRefreshRequest request) {
        String requestRefreshToken = request.getRefreshToken();
        try{
            DecodedJWT decodedJWT = jwtUtils.validateToken(requestRefreshToken);
            String username = decodedJWT.getSubject();
            String newAccessToken = jwtUtils.createToken(new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>()));
            boolean loginSuccessful = true;
            return new AuthUserResponse(username, "Token refresh sucessfully", newAccessToken, loginSuccessful);
        }catch (JWTVerificationException exception){
            throw new RuntimeException("Invalid refresh Token");
        }

    }

    public Authentication authentication(String username, String password){
        UserDetails userDetails = this.loadUserByUsername(username);

        if(userDetails == null){
            throw new BadCredentialsException("Invalid username or password");
        }

        if(!passwordEncoder.matches(password, userDetails.getPassword())){
            throw new BadCredentialsException("Invalid password");
        }
        return new UsernamePasswordAuthenticationToken(username, userDetails.getPassword(), userDetails.getAuthorities());
    }

    public AuthUserResponse createUser(CreateUser createUser) throws IllegalStateException{
        String name = createUser.getName();
        String lastName = createUser.getLastName();
        String correo = createUser.getCorreo();
        String username = createUser.getUsername();
        String password = createUser.getPassword();
        List<String> rolesRequest = createUser.getRolesApp();
        List<RolesApp> rolesAppSet = rolesRepository.findRoleAppByRoleEnumIn(rolesRequest).stream().collect(Collectors.toList());

        if(name == null || lastName == null || correo == null || username == null || password == null) {
            throw new IllegalStateException("Los campos no pueden ser null");
        }

        if(rolesRequest.isEmpty()){
            throw new IllegalStateException("The roles specified does no exist");
        }

        UsuarioApp usuarioApp = UsuarioApp.builder()
                .name(name)
                .lastName(lastName)
                .correo(correo)
                .username(username)
                .password(passwordEncoder.encode(password))
                .role(rolesAppSet)
                .isEnabled(true)
                .accountNoExpired(true)
                .accountNoLocked(true)
                .credentialNoExpired(true)
                .build();
        UsuarioApp usuarioCreate = usuarioApprRepository.save(usuarioApp);
        List<SimpleGrantedAuthority> authorities = rolesAppSet.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getRoleEnum().name())).collect(Collectors.toList());
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = new UsernamePasswordAuthenticationToken(usuarioCreate.getUsername(), null, authorities);
        String accessToken = jwtUtils.createToken(authentication);

        AuthUserResponse authUserResponse = new AuthUserResponse(username, "user Create succesfully",accessToken, true);
        return authUserResponse;
    }

}
