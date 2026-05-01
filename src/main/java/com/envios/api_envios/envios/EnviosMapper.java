package com.envios.api_envios.envios;

import com.envios.api_envios.pagos.PagosMapper;
import com.envios.api_envios.productos.ProductosMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EnviosMapper {
    private final PagosMapper pagosMapper;
    private final ProductosMapper productosMapper;

    public Envios toEntity(EnviosDTO dto) {
        Envios envio = new Envios();
        envio.setPago(pagosMapper.toEntity(dto.pago()));
        envio.setProducto(productosMapper.toEntity(dto.producto()));
        envio.setHoraSalida(dto.horaSalida());
        envio.setHoraLlegada(dto.horaLlegada());
        envio.setFechaEnvio(dto.fechaEnvio());
        envio.setNombreDestinatario(dto.nombreDestinatario());
        envio.setDniDestinatario(dto.dniDestinatario());
        envio.setNombreRemitente(dto.nombreRemitente());
        envio.setDniRemitente(dto.dniRemitente());
        envio.setEstadoEnvio(dto.estadoEnvio());
        return envio;
    }

    public EnviosDTO toDTO(Envios envio) {
        return new EnviosDTO(
                envio.getId(),
                envio.getHoraSalida(),
                envio.getHoraLlegada(),
                envio.getFechaEnvio(),
                envio.getNombreDestinatario(),
                envio.getDniDestinatario(),
                envio.getNombreRemitente(),
                envio.getDniRemitente(),
                envio.getEstadoEnvio(),
                pagosMapper.toDTO(envio.getPago()),
                productosMapper.toDTO(envio.getProducto())
        );
    }
}
