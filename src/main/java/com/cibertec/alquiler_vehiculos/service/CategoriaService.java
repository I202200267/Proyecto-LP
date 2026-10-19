package com.cibertec.alquiler_vehiculos.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cibertec.alquiler_vehiculos.model.Categoria;
import com.cibertec.alquiler_vehiculos.repository.CategoriaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoriaService {
	
	private final CategoriaRepository categoriaRepository;
	
	public List<Categoria> getAll() {
		return categoriaRepository.findAll();
	}
	
	public Categoria getOne(Integer id) {
		return categoriaRepository.findById(id).orElse(null);
	}
}