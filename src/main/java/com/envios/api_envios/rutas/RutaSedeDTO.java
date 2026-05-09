package com.envios.api_envios.rutas;

public record RutaSedeDTO(
        Long id,
        Long sedeOrigenId,
        String sedeOrigenNombre,
        Long sedeDestinoId,
        String sedeDestinoNombre,
        Boolean activo
) {
}
