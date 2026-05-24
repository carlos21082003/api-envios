package com.envios.api_envios.solicitud;

import java.time.LocalDateTime;

public record SolicitudDTO(
        Long id,
        TipoSolicitud tipo,
        String nombreSolicitante,
        String dniSolicitante,
        String telefono,
        String direccion,
        String referencia,
        String descripcionProducto,
        LocalDateTime fechaSolicitada,
        LocalDateTime fechaAtencion,
        EstadoSolicitud estado,
        String motivoRechazo,
        Long sedeId,
        String sedeNombre,
        Long envioId,
        String codigoEnvio,
        String nombrePersonaRecibe,
        String dniPersonaRecibe,
        Long sedeDestinoId,
        String sedeDestinoNombre
) {
}
