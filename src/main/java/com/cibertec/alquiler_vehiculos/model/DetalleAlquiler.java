package com.cibertec.alquiler_vehiculos.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

import org.hibernate.annotations.DynamicInsert;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_detalle_alquiler")
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@Getter @Setter

public class DetalleAlquiler {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle")
    private Integer idDetalle;

    @ManyToOne
    @JoinColumn(name = "id_alquiler")
    private Alquiler alquiler;

    @ManyToOne
    @JoinColumn(name = "id_vehiculo")
    private Vehiculo vehiculo;

    @Column(name = "dias")
    private Integer dias;

    @Column(name = "precio_unitario")
    private BigDecimal precioUnitario;

    @Column(name = "subtotal")
    private BigDecimal subtotal;
    
    // este constructor sin idDetalle para facilitar la creacion de instancias antes de guardar
    public DetalleAlquiler(Alquiler alquiler, Vehiculo vehiculo, Integer dias, BigDecimal precioUnitario, BigDecimal subtotal) {
        this.alquiler = alquiler;
        this.vehiculo = vehiculo;
        this.dias = dias;
        this.precioUnitario = precioUnitario;
        this.subtotal = subtotal;
    }
}