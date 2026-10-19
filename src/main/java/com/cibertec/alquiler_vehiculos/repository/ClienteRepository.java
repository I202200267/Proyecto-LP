package com.cibertec.alquiler_vehiculos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cibertec.alquiler_vehiculos.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
	
	List<Cliente> findAllByOrderByIdClienteDesc();
	
	// para buscar cliente por DNI en el módulo de alquileres
	Cliente findByDni(String dni);
}