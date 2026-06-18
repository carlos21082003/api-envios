package com.envios.api_envios.envios;

import java.time.LocalDateTime;
import java.util.List;

import com.envios.api_envios.pagos.PagosDTO;
import com.envios.api_envios.productos.ProductosDTO;

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
        Long sedeId,          // sede origen (compatibilidad)
        String sedeNombre,
        String nombrePersonaAutorizada,
        String dniPersonaAutorizada,
        Long sedeOrigenId,    // ✅ sede origen explícito
        Long sedeDestinoId    // ✅ sede destino
) {}