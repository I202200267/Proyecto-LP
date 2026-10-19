package com.cibertec.alquiler_vehiculos.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cibertec.alquiler_vehiculos.model.Rol;
import com.cibertec.alquiler_vehiculos.repository.RolRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RolService {

	private final RolRepository rolRepository;

	public List<Rol> getAll() {
		return rolRepository.findAll();
	}

	public Rol getOne(Integer id) {
		return rolRepository.findById(id).orElse(null);
	}
}
