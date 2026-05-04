package com.envios.api_envios.tipoProducto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "tipo_productos")
@AllArgsConstructor
@NoArgsConstructor
public class TipoProducto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private Double precioBase;
    private String descripcion;
    private Boolean activo = true;
}
