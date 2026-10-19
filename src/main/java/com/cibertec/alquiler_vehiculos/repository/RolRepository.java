package com.cibertec.alquiler_vehiculos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cibertec.alquiler_vehiculos.model.Rol;

public interface RolRepository extends JpaRepository<Rol, Integer> {
}