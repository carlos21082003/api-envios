package com.envios.api_envios.auditoria;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "auditoria")
@AllArgsConstructor
@NoArgsConstructor
public class Auditoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String metodo;
    private String endpoint;
    private Integer statusCode;
    private String dniUsuario;
    private String rolUsuario;
    private String ipOrigen;
    private String mensajeError;

    @Column(columnDefinition = "TEXT")
    private String requestBody;

    private Long duracionMs;
    private LocalDateTime fecha;
}
