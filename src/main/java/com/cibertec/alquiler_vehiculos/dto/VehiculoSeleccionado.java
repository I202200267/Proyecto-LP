package com.cibertec.alquiler_vehiculos.dto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class VehiculoSeleccionado {
    private Integer idVehiculo;
    private String placa;
    private String modelo;
    private BigDecimal precioDiario;
    private Integer dias;
    
    // Calcular el subtotal automáticamente multiplicando precio * días
    public BigDecimal getSubtotal() {
        if (precioDiario != null && dias != null) {
            return precioDiario.multiply(new BigDecimal(dias));
        }
        return BigDecimal.ZERO;
    }
}