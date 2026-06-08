package com.envios.api_envios.usuarios;

public record UsuariosDTO(
        Long id,
        String nombre,
        String dni,
        String password,
        String telefono,
        Roles rol,
        Long sedeId,
        String sedeNombre,
        Boolean activo
) {
}
