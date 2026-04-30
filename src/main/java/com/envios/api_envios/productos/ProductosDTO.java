package com.envios.api_envios.productos;

public record ProductosDTO(
        Long id,
        String  tipoProducto,
        String descripcion,
        Double numeroPaquetes
) {
}
