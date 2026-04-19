package com.envios.api_envios.pagos;

import java.time.LocalDateTime;

public record PagosDTO(
        Long id,
        double monto,
        String metodoPago,
        LocalDateTime fechaPago,
        EstadoPago estadoPago
) {
}
