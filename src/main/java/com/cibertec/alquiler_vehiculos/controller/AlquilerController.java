package com.cibertec.alquiler_vehiculos.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cibertec.alquiler_vehiculos.dto.AlquilerFilter;
import com.cibertec.alquiler_vehiculos.dto.VehiculoSeleccionado;
import com.cibertec.alquiler_vehiculos.model.Alquiler;
import com.cibertec.alquiler_vehiculos.model.Cliente;
import com.cibertec.alquiler_vehiculos.model.DetalleAlquiler;
import com.cibertec.alquiler_vehiculos.model.Usuario;
import com.cibertec.alquiler_vehiculos.service.AlquilerService;
import com.cibertec.alquiler_vehiculos.service.ClienteService;
import com.cibertec.alquiler_vehiculos.service.UsuarioService;
import com.cibertec.alquiler_vehiculos.service.VehiculoService;
import com.cibertec.alquiler_vehiculos.util.Alert;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("alquiler")
@RequiredArgsConstructor
@SessionAttributes({ "seleccionados", "cliente" })

public class AlquilerController {

	private final VehiculoService vehiculoService;
	private final ClienteService clienteService;
	private final UsuarioService usuarioService;
	private final AlquilerService alquilerService;

	@ModelAttribute("seleccionados")
	public List<VehiculoSeleccionado> inicio() {
		return new ArrayList<VehiculoSeleccionado>();
	}

	@GetMapping("listado")
	public String listado(Model model, HttpSession session) {
		model.addAttribute("lstAlquileres", alquilerService.search(null));
		return "alquiler/listado";
	}

	@GetMapping("nuevo")
	public String nuevo(Model model) {
		model.addAttribute("vehiculos", vehiculoService.getAllActive());
		model.addAttribute("vehiculoSeleccionado", new VehiculoSeleccionado());
		return "alquiler/nuevo";
	}

	@GetMapping("validar-cliente")
	public String validarCliente(@RequestParam(required = false) String accion,
			@RequestParam(required = false) String dni, RedirectAttributes flash, HttpSession session,
			SessionStatus sessionStatus) {

		if ("limpiar".equals(accion)) {
			session.removeAttribute("cliente");
			sessionStatus.setComplete();
			return "redirect:/alquiler/nuevo";
		}

		if (dni == null || dni.trim().isEmpty()) {
			flash.addFlashAttribute("alert", Alert.sweetAlertInfo("Ingrese un DNI válido"));
			return "redirect:/alquiler/nuevo";
		}

		Cliente cliente = clienteService.GetOneByDni(dni);

		if (cliente == null) {
			flash.addFlashAttribute("alert", Alert.sweetAlertInfo("Cliente no encontrado"));
			return "redirect:/alquiler/nuevo";
		}

		session.setAttribute("cliente", cliente);
		return "redirect:/alquiler/nuevo";
	}

	@PostMapping("agregar")
	public String agregar(@ModelAttribute VehiculoSeleccionado vehiculoSeleccionado,
			@ModelAttribute("seleccionados") List<VehiculoSeleccionado> seleccionados, RedirectAttributes flash) {

		var vehiculoDb = vehiculoService.getOne(vehiculoSeleccionado.getIdVehiculo());
		
		if (vehiculoDb != null && !"DISPONIBLE".equalsIgnoreCase(vehiculoDb.getEstado())) {
			flash.addFlashAttribute("alert", Alert.sweetAlertInfo("No se puede alquilar un vehículo en estado " + vehiculoDb.getEstado().toLowerCase()));
			return "redirect:/alquiler/nuevo";
		}

		boolean existe = seleccionados.stream()
				.anyMatch(v -> v.getIdVehiculo().equals(vehiculoSeleccionado.getIdVehiculo()));

		if (existe) {
			flash.addFlashAttribute("alert", Alert.sweetAlertInfo("El vehículo se encuentra en la lista"));
			return "redirect:/alquiler/nuevo";
		}

		seleccionados.add(vehiculoSeleccionado);
		return "redirect:/alquiler/nuevo";
	}

	@PostMapping("remover")
	public String remover(@RequestParam Integer id,
			@ModelAttribute("seleccionados") List<VehiculoSeleccionado> seleccionados) {

		seleccionados.removeIf(v -> v.getIdVehiculo().equals(id));
		return "redirect:/alquiler/nuevo";
	}

	@PostMapping("registrar")
	public String registrar(@ModelAttribute Alquiler alquiler,
			@ModelAttribute("seleccionados") List<VehiculoSeleccionado> seleccionados, RedirectAttributes flash,
			HttpSession session, SessionStatus sessionStatus) {

		Integer idUsuario = (Integer) session.getAttribute("ID_USUARIO");

		if (idUsuario == null) {
			flash.addFlashAttribute("alert", Alert.sweetAlertInfo("Sesión caducada"));
			return "redirect:/";
		}

		Usuario usuario = usuarioService.getOne(idUsuario);
		alquiler.setUsuario(usuario);

		Cliente cliente = (Cliente) session.getAttribute("cliente");

		if (cliente == null) {
			flash.addFlashAttribute("alert", Alert.sweetAlertInfo("Encuentre a un cliente por su DNI"));
			return "redirect:/alquiler/nuevo";
		}

		alquiler.setCliente(cliente);

		if (seleccionados.isEmpty()) {
			flash.addFlashAttribute("alert", Alert.sweetAlertInfo("Agregue como mín 1 vehículo"));
			return "redirect:/alquiler/nuevo";
		}

		alquiler.setFechaAlquiler(java.time.LocalDateTime.now());
		alquiler.setFechaInicio(java.time.LocalDate.now());
		alquiler.setFechaFin(java.time.LocalDate.now().plusDays(1));
		alquiler.setEstado("REGISTRADO");

		List<DetalleAlquiler> lstDetalle = seleccionados.stream().map(item -> {
			var vehiculo = vehiculoService.getOne(item.getIdVehiculo());
			return new DetalleAlquiler(alquiler, vehiculo, item.getDias(), item.getPrecioDiario(), item.getSubtotal());
		}).toList();

		alquiler.setLstDetalleAlquiler(lstDetalle);

		java.math.BigDecimal montoTotal = lstDetalle.stream().map(DetalleAlquiler::getSubtotal)
				.filter(java.util.Objects::nonNull).reduce(java.math.BigDecimal.ZERO, java.math.BigDecimal::add);

		alquiler.setMontoTotal(montoTotal);

		var response = alquilerService.create(alquiler);

		if (response.success()) {
			sessionStatus.setComplete();
		}

		flash.addFlashAttribute("alert", Alert.sweetToast(response.mensaje(), "success", 5000));
		return "redirect:/alquiler/listado";
	}
}