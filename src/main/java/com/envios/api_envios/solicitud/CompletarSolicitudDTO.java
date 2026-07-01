package com.envios.api_envios.solicitud;

import com.envios.api_envios.pagos.EstadoPago;
import com.envios.api_envios.pagos.MetodoPago;

public record CompletarSolicitudDTO(
        String horaSalida,
        String horaLlegada,
        Long tipoProductoId,
        Double numeroPaquetes,
        Double peso,
        Double volumen,
        MetodoPago metodoPago,
        EstadoPago estadoPago
) {}
