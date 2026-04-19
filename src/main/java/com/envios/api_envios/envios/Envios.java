package com.envios.api_envios.envios;

import com.envios.api_envios.pagos.Pagos;
import com.envios.api_envios.productos.Productos;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "envios")
@AllArgsConstructor
@NoArgsConstructor

public class Envios {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "pago_id")
    private Pagos pago;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "producto_id")
    private Productos producto;

    private String horaSalida;
    private String horaLlegada;
    private LocalDateTime fechaEnvio;
    private String nombreDestinatario;
    private String dniDestinatario;
    private String nombreRemitente;
    private String dniRemitente; //codigo de rastreo
    private EstadoEnvio estadoEnvio;
}
