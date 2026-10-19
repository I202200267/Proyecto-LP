package com.cibertec.alquiler_vehiculos.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cibertec.alquiler_vehiculos.dto.AutenticacionFilter;
import com.cibertec.alquiler_vehiculos.service.AutenticacionService;
import com.cibertec.alquiler_vehiculos.util.Alert;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final AutenticacionService autenticacionService;

    @GetMapping("/")
    public String root(Model model) {
        model.addAttribute("filter", new AutenticacionFilter(null, null));
        return "login";
    }

    @GetMapping("iniciar-sesion")
    public String iniciarSesion(@ModelAttribute AutenticacionFilter filter, RedirectAttributes flash,
            HttpSession session) {

        var usuario = autenticacionService.autenticathe(filter);

        if (usuario == null) {
            var alert = Alert.sweetAlertError("Correo y/o clave inválidos");
            flash.addFlashAttribute("alert", alert);
            return "redirect:/";
        }

        session.setAttribute("FULL_NAME", usuario.getNombre());
        if (usuario.getRol() != null) {
            session.setAttribute("ID_TIPO", usuario.getRol().getIdRol());
        }
        session.setAttribute("ID_USUARIO", usuario.getIdUsuario());

        String routeImage = "/imagenes/bienvenido.gif";
        var alert = Alert.sweetImageUrl("Bienvenido al Sistema", usuario.getNombre(), routeImage);
        flash.addFlashAttribute("alert", alert);
        return "redirect:/alquiler/nuevo";
    }

    @GetMapping("cerrar-sesion")
    public String cerrarSesion(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}