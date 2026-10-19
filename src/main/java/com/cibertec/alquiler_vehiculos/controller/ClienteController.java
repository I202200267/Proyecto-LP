package com.cibertec.alquiler_vehiculos.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cibertec.alquiler_vehiculos.model.Cliente;
import com.cibertec.alquiler_vehiculos.service.ClienteService;
import com.cibertec.alquiler_vehiculos.util.Alert;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("cliente")
@RequiredArgsConstructor
public class ClienteController {

	private final ClienteService clienteService;

	@GetMapping("listado")
	public String listado(Model model) {
		model.addAttribute("lstClientes", clienteService.getAll());
		return "cliente/listado";
	}

	@GetMapping("nuevo")
	public String nuevo(Model model) {
		model.addAttribute("cliente", new Cliente());
		return "cliente/nuevo";
	}

	@PostMapping("registrar")
	public String registrar(@ModelAttribute Cliente cliente, RedirectAttributes flash) {
		var response = clienteService.create(cliente);
		flash.addFlashAttribute("alert", Alert.sweetToast(response.mensaje(), "success", 5000));

		return "redirect:/cliente/listado";
	}

	@GetMapping("edicion/{id}")
	public String edicion(@PathVariable Integer id, Model model) {
		model.addAttribute("cliente", clienteService.getOne(id));
		return "cliente/edicion";
	}

	@PostMapping("guardar")
	public String guardar(@ModelAttribute Cliente cliente, RedirectAttributes flash) {
		var response = clienteService.update(cliente);
		flash.addFlashAttribute("alert", Alert.sweetToast(response.mensaje(), "success", 5000));

		return "redirect:/cliente/listado";
	}
}
