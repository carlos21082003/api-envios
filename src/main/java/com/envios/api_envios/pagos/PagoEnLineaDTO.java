package com.envios.api_envios.pagos;

public record PagoEnLineaDTO(
        String numeroTarjeta,
        String nombreTitular,
        String mesExpiracion,
        String anioExpiracion
) {
}
