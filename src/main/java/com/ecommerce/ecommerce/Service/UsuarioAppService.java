package com.ecommerce.ecommerce.Service;

import com.ecommerce.ecommerce.repository.UsuarioApprRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioAppService {

    @Autowired
    private UsuarioApprRepository usuarioApprRepository;



}
