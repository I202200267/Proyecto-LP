package com.cibertec.alquiler_vehiculos.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cibertec.alquiler_vehiculos.dto.AlquilerFilter;
import com.cibertec.alquiler_vehiculos.dto.ResultadoResponse;
import com.cibertec.alquiler_vehiculos.model.Alquiler;
import com.cibertec.alquiler_vehiculos.repository.AlquilerRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AlquilerService {

    private final AlquilerRepository alquilerRepository;

    public List<Alquiler> search(AlquilerFilter filter) {
        return alquilerRepository.findAll();
    }

    @Transactional
    public ResultadoResponse create(Alquiler alquiler) {
        try {
            var registrado = alquilerRepository.save(alquiler);
            var mensaje = String.format("Alquiler N° %d registrado exitosamente", registrado.getIdAlquiler());
            
            return new ResultadoResponse(true, mensaje);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultadoResponse(false, "Hubo un error al registrar el alquiler");
        }
    }
}
