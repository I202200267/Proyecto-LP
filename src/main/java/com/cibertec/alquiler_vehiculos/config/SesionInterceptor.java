package com.cibertec.alquiler_vehiculos.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class SesionInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(false);
        
        // Verifica si la sesión existe y si el atributo ID_USUARIO sigue presente
        boolean sesionValida = (session != null && session.getAttribute("ID_USUARIO") != null);
        
        if (!sesionValida) {
            // Si la sesión expiró o no existe, redirige al login raíz ("/")
            response.sendRedirect(request.getContextPath() + "/");
            return false;
        }
        
        return true;
    }
}