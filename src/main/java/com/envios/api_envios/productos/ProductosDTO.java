package com.envios.api_envios.productos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ProductosDTO(
        Long id,

        @NotNull(message = "El tipo de producto es requerido")
        Long tipoProductoId,

        String tipoProductoNombre,

        @NotBlank(message = "La descripción es requerida")
        String descripcion,

        @NotNull(message = "El número de paquetes es requerido")
        @Positive(message = "El número de paquetes debe ser mayor a 0")
        Double numeroPaquetes,

        @NotNull(message = "El peso es requerido")
        @Positive(message = "El peso debe ser mayor a 0")
        Double peso,

        @NotNull(message = "El volumen es requerido")
        @Positive(message = "El volumen debe ser mayor a 0")
        Double volumen,

        Double precioPorProducto
) {
}
