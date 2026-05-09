package com.envios.api_envios.solicitud;

import com.envios.api_envios.envios.Envios;
import com.envios.api_envios.sede.Sede;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "solicitudes_domicilio")
@AllArgsConstructor
@NoArgsConstructor
public class SolicitudDomicilio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TipoSolicitud tipo; // RECOJO o DELIVERY

    // datos del solicitante
    private String nombreSolicitante;
    private String dniSolicitante;
    private String telefono;
    private String direccion;
    private String referencia;

    private String descripcionProducto;
    private LocalDateTime fechaSolicitada;
    private LocalDateTime fechaAtencion;

    @Enumerated(EnumType.STRING)
    private EstadoSolicitud estado;

    private String motivoRechazo; // si fue rechazada

    @ManyToOne
    @JoinColumn(name = "sede_id")
    private Sede sede; // sede que debe atender

    @ManyToOne
    @JoinColumn(name = "envio_id")
    private Envios envio;

    private String nombrePersonaRecibe;
    private String dniPersonaRecibe;
}
