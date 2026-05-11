package com.envios.api_envios.envios;

import com.envios.api_envios.pagos.PagosMapper;
import com.envios.api_envios.productos.ProductosMapper;
import com.envios.api_envios.sede.Sede;
import com.envios.api_envios.sede.SedeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EnviosMapper {
    private final PagosMapper pagosMapper;
    private final ProductosMapper productosMapper;
    private final SedeRepository sedeRepository;

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
        envio.setProvincia(dto.provincia());
        envio.setNombrePersonaAutorizada(dto.nombrePersonaAutorizada());
        envio.setDniPersonaAutorizada(dto.dniPersonaAutorizada());

        if (dto.sedeId() != null) {
            Sede sede = sedeRepository.findById(dto.sedeId())
                    .orElseThrow(() -> new IllegalArgumentException("Sede no encontrada"));
            envio.setSede(sede);
        }

        if (dto.sedeDestinoId() != null) {
            Sede sedeDestino = sedeRepository.findById(dto.sedeDestinoId())
                    .orElseThrow(() -> new IllegalArgumentException("Sede destino no encontrada"));
            envio.setSedeDestino(sedeDestino);
        }
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
                productosMapper.toDTO(envio.getProducto()),
                envio.getProvincia(),
                envio.getSede() != null ? envio.getSede().getId() : null,
                envio.getSede() != null ? envio.getSede().getNombre() : null,
                envio.getNombrePersonaAutorizada(),
                envio.getDniPersonaAutorizada(),
                envio.getSedeDestino() != null ? envio.getSedeDestino().getId() : null,
                envio.getSedeDestino() != null ? envio.getSedeDestino().getId() : null
        );
    }
}
