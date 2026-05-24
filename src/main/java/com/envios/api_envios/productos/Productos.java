package com.envios.api_envios.productos;

import com.envios.api_envios.tipoProducto.TipoProducto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "productos")
@AllArgsConstructor
@NoArgsConstructor

public class Productos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tipo_producto_id")
    private TipoProducto tipoProducto;

    private String descripcion;
    private Double numeroPaquetes;

    @Column(name = "envio_id")
    private Long envioId;

    @Transient
    public Double getPrecioPorProducto() {
        if (tipoProducto == null || numeroPaquetes == null) return 0.0;
        return tipoProducto.getPrecioBase() * numeroPaquetes;
    }
}
