package com.cibertec.alquiler_vehiculos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cibertec.alquiler_vehiculos.model.Alquiler;

public interface AlquilerRepository extends JpaRepository<Alquiler, Integer> {
	
	List<Alquiler> findAllByOrderByIdAlquilerDesc();
	
	List<Alquiler> findByUsuario_IdUsuarioOrderByIdAlquilerDesc(Integer idUsuario);
}