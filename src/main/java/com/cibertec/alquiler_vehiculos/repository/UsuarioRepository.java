package com.cibertec.alquiler_vehiculos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.cibertec.alquiler_vehiculos.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    
    Usuario findByEmailAndPassword(String cuenta, String clave);
}