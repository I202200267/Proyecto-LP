package com.cibertec.alquiler_vehiculos.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cibertec.alquiler_vehiculos.dto.ResultadoResponse;
import com.cibertec.alquiler_vehiculos.model.Usuario;
import com.cibertec.alquiler_vehiculos.repository.UsuarioRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService {

	private final UsuarioRepository usuarioRepository;

	public List<Usuario> getAll() {
		return usuarioRepository.findAll();
	}

	public Usuario getOne(Integer id) {
		return usuarioRepository.findById(id).orElse(null);
	}

	@Transactional
	public ResultadoResponse create(Usuario usuario) {
		try {
			var registrado = usuarioRepository.save(usuario);
			var mensaje = String.format("Usuario %s registrado exitosamente", registrado.getNombre());

			return new ResultadoResponse(true, mensaje);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultadoResponse(false, "Hubo un error en la transacción");
		}
	}
}
