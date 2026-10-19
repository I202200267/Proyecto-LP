package com.cibertec.alquiler_vehiculos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cibertec.alquiler_vehiculos.model.Vehiculo;

public interface VehiculoRepository extends JpaRepository<Vehiculo, Integer> {
    List<Vehiculo> findAllByOrderByIdVehiculoDesc();
}