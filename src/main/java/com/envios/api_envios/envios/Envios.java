package com.envios.api_envios.envios;

import com.envios.api_envios.pagos.Pagos;
import com.envios.api_envios.productos.Productos;
import com.envios.api_envios.sede.Sede;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "envios")
@AllArgsConstructor
@NoArgsConstructor

public class Envios {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String codigoEnvio;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "pago_id")
    private Pagos pago;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "envio_id")
    private List<Productos> productos = new ArrayList<>();

    private String horaSalida;
    private String horaLlegada;
    private LocalDateTime fechaEnvio;
    private String nombreDestinatario;
    private String dniDestinatario;
    private String nombreRemitente;
    private String dniRemitente; //codigo de rastreo
    private EstadoEnvio estadoEnvio;
    private String provincia;

    @ManyToOne
    @JoinColumn(name = "sede_id")
    private Sede sede;

    private String nombrePersonaAutorizada;
    private String dniPersonaAutorizada;

    // Envios.java — agrega esto
    @ManyToOne
    @JoinColumn(name = "sede_destino_id")
    private Sede sedeDestino;
}
