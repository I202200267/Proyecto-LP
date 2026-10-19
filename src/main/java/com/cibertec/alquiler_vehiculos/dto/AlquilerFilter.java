package com.cibertec.alquiler_vehiculos.dto;

public record AlquilerFilter(Integer idUsuario, String dniCliente) {
    public AlquilerFilter withIdUsuario(Integer idUsuario) {
        return new AlquilerFilter(idUsuario, this.dniCliente);
    }
}