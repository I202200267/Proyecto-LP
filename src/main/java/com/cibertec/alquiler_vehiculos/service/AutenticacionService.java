package com.cibertec.alquiler_vehiculos.service;

import org.springframework.stereotype.Service;
import com.cibertec.alquiler_vehiculos.dto.AutenticacionFilter;
import com.cibertec.alquiler_vehiculos.model.Usuario;
import com.cibertec.alquiler_vehiculos.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AutenticacionService {
    
    private final UsuarioRepository usuarioRepository;
    
    public Usuario autenticathe(AutenticacionFilter filter) {
        return usuarioRepository.findByEmailAndPassword(filter.email(), filter.password());
    }
}