package com.envios.api_envios.tipoProducto;

public record TipoProductoDTO(
        Long id,
        String nombre,
        Double precioBase,
        String descripcion
) {
}
