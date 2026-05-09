package com.envios.api_envios.jwt;

public record TokenDTO(
        String token,
        String dni,
        String rol,
        String nombre,
        Long sedeId,
        String sedeNombre
) {
}
