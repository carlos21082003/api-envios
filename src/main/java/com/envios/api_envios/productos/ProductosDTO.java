package com.envios.api_envios.productos;

public record ProductosDTO(
        Long id,
        Long tipoProductoId,
        String tipoProductoNombre,
        String descripcion,
        Double numeroPaquetes,
        Double peso,
        Double volumen,
        Double precioPorProducto
) {
}
