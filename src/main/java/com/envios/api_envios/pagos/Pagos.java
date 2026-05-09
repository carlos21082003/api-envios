package com.envios.api_envios.pagos;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@Entity
@Table(name = "pagos")
@AllArgsConstructor
@NoArgsConstructor

public class Pagos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double monto;
    private String metodoPago;
    private LocalDateTime fechaPago;
    @Enumerated(EnumType.STRING)
    private EstadoPago estadoPago;
}
