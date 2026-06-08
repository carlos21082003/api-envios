package com.envios.api_envios.envios;

import com.envios.api_envios.pagos.PagosDTO;
import com.envios.api_envios.productos.ProductosDTO;

import java.time.LocalDateTime;
import java.util.List;

public record EnviosDTO(
        Long id,
        String codigoEnvio,
        String horaSalida,
        String horaLlegada,
        LocalDateTime fechaEnvio,
        String nombreDestinatario,
        String dniDestinatario,
        String nombreRemitente,
        String dniRemitente,
        EstadoEnvio estadoEnvio,
        PagosDTO pago,
        List<ProductosDTO> productos,
        String provincia,
        Double pesoTotal,
        Double volumenTotal,
        Long sedeId,
        String sedeNombre,
        String nombrePersonaAutorizada,
        String dniPersonaAutorizada,
        Long sedeOrigenId,
        Long sedeDestinoId
) {
}
