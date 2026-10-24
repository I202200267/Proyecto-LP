package com.cibertec.alquiler_vehiculos.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final SesionInterceptor sesionInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(sesionInterceptor)
                .addPathPatterns("/home/**", "/vehiculo/**", "/cliente/**", "/alquiler/**")
                .excludePathPatterns("/", "/iniciar-sesion", "/css/**", "/imagenes/**", "/js/**");
    }
}
