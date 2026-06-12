package com.envios.api_envios.pagos;

import java.time.LocalDateTime;

public record PagosDTO(
        Long id,
        Double monto,
        MetodoPago metodoPago,
        LocalDateTime fechaPago,
        EstadoPago estadoPago
) {
}
