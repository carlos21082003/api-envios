package com.envios.api_envios.tarifa;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "tarifas_adicionales")
@NoArgsConstructor
@AllArgsConstructor
public class TarifaAdicional {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double limitePeso;      // ej: 5.0 kg
    private Double recargoPeso;     // soles por kg extra
    private Double limiteVolumen;   // ej: 0.5 m³
    private Double recargoVolumen;  // soles por m³ extra
}
