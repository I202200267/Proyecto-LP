package com.cibertec.alquiler_vehiculos.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cibertec.alquiler_vehiculos.dto.ResultadoResponse;
import com.cibertec.alquiler_vehiculos.model.Vehiculo;
import com.cibertec.alquiler_vehiculos.repository.VehiculoRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VehiculoService {
	
	private final VehiculoRepository vehiculoRepository;
	
	public List<Vehiculo> getAll() {
		return vehiculoRepository.findAll();
	}

	public List<Vehiculo> getAllActive() {
		return vehiculoRepository.findAll();
	}
	
	@Transactional
	public ResultadoResponse create(Vehiculo vehiculo) {
		try {
			var registrado = vehiculoRepository.save(vehiculo);
			var mensaje = String.format("Vehículo con Placa %s registrado exitosamente", registrado.getPlaca());
			
			return new ResultadoResponse(true, mensaje);	
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultadoResponse(false, "Hubo un error en la transacción");
		}
	}
	
	public Vehiculo getOne(Integer id) {
		return vehiculoRepository.findById(id).orElse(null);
	}
	
	@Transactional
	public ResultadoResponse update(Vehiculo vehiculo) {
		try {
			var registrado = vehiculoRepository.save(vehiculo);
			var mensaje = String.format("Vehículo con Placa %s actualizado exitosamente", registrado.getPlaca());
			
			return new ResultadoResponse(true, mensaje);	
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultadoResponse(false, "Hubo un error en la transacción");
		}
	}
	
	@Transactional
	public ResultadoResponse cambiarEstado(Integer id) {
		try {
			var vehiculo = vehiculoRepository.findById(id).orElse(null);
			
			if(vehiculo == null) {
				return new ResultadoResponse(false, "Vehículo no encontrado");
			}
			
			String nuevoEstado = "DISPONIBLE".equals(vehiculo.getEstado()) ? "MANTENIMIENTO" : "DISPONIBLE";
			vehiculo.setEstado(nuevoEstado);
			vehiculoRepository.save(vehiculo);
			
			var mensaje = String.format("Vehículo con Placa %s cambió a estado %s", vehiculo.getPlaca(), nuevoEstado);
			return new ResultadoResponse(true, mensaje);	
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultadoResponse(false, "Hubo un error en la transacción");
		}
	}
}
