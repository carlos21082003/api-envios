package com.envios.api_envios.solicitud;

import com.envios.api_envios.pagos.EstadoPago;

public record CompletarSolicitudDTO(
        String horaSalida,
        String horaLlegada,
        Long tipoProductoId,
        Double numeroPaquetes,
        String metodoPago,
        EstadoPago estadoPago
) {}
