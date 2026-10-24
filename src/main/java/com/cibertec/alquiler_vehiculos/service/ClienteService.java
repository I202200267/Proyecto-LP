package com.cibertec.alquiler_vehiculos.service;

import java.util.List;
import org.springframework.stereotype.Service;

import com.cibertec.alquiler_vehiculos.dto.ResultadoResponse;
import com.cibertec.alquiler_vehiculos.model.Cliente;
import com.cibertec.alquiler_vehiculos.repository.ClienteRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public List<Cliente> getAll() {
        return clienteRepository.findAll();
    }

    public Cliente getOne(Integer id) {
        return clienteRepository.findById(id).orElse(null);
    }

    // Método requerido por AlquilerController
    public Cliente GetOneByDni(String dni) {
        return clienteRepository.findByDni(dni);
    }

    @Transactional
    public ResultadoResponse create(Cliente cliente) {
        try {
            var registrado = clienteRepository.save(cliente);
            var mensaje = String.format("Cliente %s registrado exitosamente", registrado.getNombres());
            return new ResultadoResponse(true, mensaje);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultadoResponse(false, "Hubo un error en la transacción");
        }
    }

    @Transactional
    public ResultadoResponse update(Cliente cliente) {
        try {
            var actualizado = clienteRepository.save(cliente);
            var mensaje = String.format("Cliente %s actualizado exitosamente", actualizado.getNombres());
            return new ResultadoResponse(true, mensaje);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultadoResponse(false, "Hubo un error en la transacción");
        }
    }
}