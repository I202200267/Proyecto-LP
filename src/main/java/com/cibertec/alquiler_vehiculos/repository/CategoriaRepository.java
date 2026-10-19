package com.cibertec.alquiler_vehiculos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cibertec.alquiler_vehiculos.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {
}