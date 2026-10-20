package com.cibertec.alquiler_vehiculos.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_alquiler")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor

public class Alquiler {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_alquiler")
    private Integer idAlquiler;

    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @Column(name = "fecha_alquiler")
    private LocalDateTime fechaAlquiler;

    @Column(name = "fecha_inicio")
    private LocalDate fechaInicio; 

    @Column(name = "fecha_fin")
    private LocalDate fechaFin;
    
    @Column(name = "monto_total")
    private BigDecimal montoTotal;

    @Column(name = "estado")
    private String estado;

    @OneToMany(mappedBy = "alquiler", cascade = CascadeType.ALL)
    private List<DetalleAlquiler> lstDetalleAlquiler;
}