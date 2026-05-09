package com.envios.api_envios.sede;

public record SedeDTO(
        Long id,
        String nombre,
        String provincia,
        String direccion,
        String telefono,
        Boolean esPrincipal,
        Boolean tieneRecojo,
        Boolean tieneDelivery,
        Boolean activo

) {
}
