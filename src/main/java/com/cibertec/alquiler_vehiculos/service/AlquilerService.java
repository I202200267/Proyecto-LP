package com.cibertec.alquiler_vehiculos.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cibertec.alquiler_vehiculos.dto.AlquilerFilter;
import com.cibertec.alquiler_vehiculos.dto.ResultadoResponse;
import com.cibertec.alquiler_vehiculos.model.Alquiler;
import com.cibertec.alquiler_vehiculos.model.DetalleAlquiler;
import com.cibertec.alquiler_vehiculos.model.Vehiculo;
import com.cibertec.alquiler_vehiculos.repository.AlquilerRepository;
import com.cibertec.alquiler_vehiculos.repository.VehiculoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AlquilerService {

    private final AlquilerRepository alquilerRepository;
    private final VehiculoRepository vehiculoRepository;

    public List<Alquiler> search(AlquilerFilter filter) {
        return alquilerRepository.findAllByOrderByIdAlquilerDesc();
    }

    public List<Alquiler> findByUsuario(Integer idUsuario) {
        return alquilerRepository.findByUsuario_IdUsuarioOrderByIdAlquilerDesc(idUsuario);
    }

    @Transactional
    public ResultadoResponse create(Alquiler alquiler) {
        try {
            var registrado = alquilerRepository.save(alquiler);

            if (alquiler.getLstDetalleAlquiler() != null) {
                for (DetalleAlquiler detalle : alquiler.getLstDetalleAlquiler()) {
                    Vehiculo vehiculo = detalle.getVehiculo();
                    if (vehiculo != null) {
                        vehiculo.setEstado("ALQUILADO");
                        vehiculoRepository.save(vehiculo);
                    }
                }
            }

            var mensaje = String.format("Alquiler N° %d registrado exitosamente", registrado.getIdAlquiler());
            return new ResultadoResponse(true, mensaje);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultadoResponse(false, "Hubo un error al registrar el alquiler");
        }
    }
}