package com.envios.api_envios.sede;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "sedes")
@AllArgsConstructor
@NoArgsConstructor
public class Sede {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String provincia;
    private String direccion;
    private String telefono;
    private Boolean esPrincipal = false;
    private Boolean tieneRecojo = false;
    private Boolean tieneDelivery = false;
    private Boolean activo = true;
}
