package com.cibertec.alquiler_vehiculos.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cibertec.alquiler_vehiculos.model.Vehiculo;
import com.cibertec.alquiler_vehiculos.service.CategoriaService;
import com.cibertec.alquiler_vehiculos.service.VehiculoService;
import com.cibertec.alquiler_vehiculos.util.Alert;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("vehiculo")
@RequiredArgsConstructor
public class VehiculoController {
	
	private final VehiculoService vehiculoService;
	private final CategoriaService categoriaService;
	
	@GetMapping("listado")
	public String listado(Model model) {
		model.addAttribute("lstVehiculos", vehiculoService.getAll());
		return "vehiculo/listado";
	}
	
	@GetMapping("nuevo")
	public String nuevo(Model model) {
		model.addAttribute("lstCategorias", categoriaService.getAll());
		model.addAttribute("vehiculo", new Vehiculo());
		return "vehiculo/nuevo";
	}
	
	@PostMapping("registrar")
	public String registrar(@ModelAttribute Vehiculo vehiculo, RedirectAttributes flash) {
		var response = vehiculoService.create(vehiculo);
		flash.addFlashAttribute("alert", Alert.sweetToast(response.mensaje(), "success", 5000));
		
		return "redirect:/vehiculo/listado";
	}
	
	@GetMapping("edicion/{id}")
	public String edicion(@PathVariable Integer id, Model model, HttpSession session, RedirectAttributes flash) {
		Integer idTipo = (Integer) session.getAttribute("ID_TIPO");
		
		if (idTipo != null && idTipo == 2) {
			flash.addFlashAttribute("alert", Alert.sweetAlertInfo("Acceso denegado: Los cajeros no pueden editar vehículos."));
			return "redirect:/vehiculo/listado";
		}
		
		model.addAttribute("lstCategorias", categoriaService.getAll());
		model.addAttribute("vehiculo", vehiculoService.getOne(id));
		return "vehiculo/edicion";
	}
	
	@PostMapping("guardar")
	public String guardar(@ModelAttribute Vehiculo vehiculo, RedirectAttributes flash, HttpSession session) {
		Integer idTipo = (Integer) session.getAttribute("ID_TIPO");
		
		if (idTipo != null && idTipo == 2) {
			flash.addFlashAttribute("alert", Alert.sweetAlertInfo("Acceso denegado: Los cajeros no pueden modificar vehículos."));
			return "redirect:/vehiculo/listado";
		}
		
		var response = vehiculoService.update(vehiculo);
		flash.addFlashAttribute("alert", Alert.sweetToast(response.mensaje(), "success", 5000));
		
		return "redirect:/vehiculo/listado";
	}

	@PostMapping("cambiar-estado")
	public String cambiarEstado(@RequestParam Integer id, RedirectAttributes flash, HttpSession session) {
		var response = vehiculoService.cambiarEstado(id);
		flash.addFlashAttribute("alert", Alert.sweetToast(response.mensaje(), "success", 5000));
		
		return "redirect:/vehiculo/listado";
	}
}