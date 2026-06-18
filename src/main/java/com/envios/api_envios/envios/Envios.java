package com.envios.api_envios.envios;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.envios.api_envios.pagos.Pagos;
import com.envios.api_envios.productos.Productos;
import com.envios.api_envios.sede.Sede;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private String dniRemitente;
    private EstadoEnvio estadoEnvio;
    private String provincia;

    @Column(name = "peso_total")
    private Double pesoTotal;

    @Column(name = "volumen_total")
    private Double volumenTotal;

    // Sede origen
    @ManyToOne
    @JoinColumn(name = "sede_id")
    private Sede sede;

    private String nombrePersonaAutorizada;
    private String dniPersonaAutorizada;

    // ✅ Sede destino agregada
    @ManyToOne
    @JoinColumn(name = "sede_destino_id")
    private Sede sedeDestino;
}