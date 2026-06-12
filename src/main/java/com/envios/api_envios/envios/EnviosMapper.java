package com.envios.api_envios.envios;

import com.envios.api_envios.pagos.PagosMapper;
import com.envios.api_envios.productos.Productos;
import com.envios.api_envios.productos.ProductosMapper;
import com.envios.api_envios.sede.Sede;
import com.envios.api_envios.sede.SedeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class EnviosMapper {
    private final PagosMapper pagosMapper;
    private final ProductosMapper productosMapper;
    private final SedeRepository sedeRepository;

    public Envios toEntity(EnviosDTO dto) {
        Envios envio = new Envios();
        envio.setPago(pagosMapper.toEntity(dto.pago()));

        // Mapear lista de productos
        if (dto.productos() != null) {
            List<Productos> productos = dto.productos().stream()
                    .map(productosMapper::toEntity)
                    .collect(Collectors.toList());
            envio.setProductos(productos);
        }

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
                envio.getCodigoEnvio(),
                envio.getHoraSalida(),
                envio.getHoraLlegada(),
                envio.getFechaEnvio(),
                envio.getNombreDestinatario(),
                envio.getDniDestinatario(),
                envio.getNombreRemitente(),
                envio.getDniRemitente(),
                envio.getEstadoEnvio(),
                pagosMapper.toDTO(envio.getPago()),
                envio.getProductos().stream()
                        .map(productosMapper::toDTO)
                        .collect(Collectors.toList()),
                envio.getProvincia(),
                envio.getPesoTotal(),
                envio.getVolumenTotal(),
                envio.getSede() != null ? envio.getSede().getId() : null,
                envio.getSede() != null ? envio.getSede().getNombre() : null,
                envio.getNombrePersonaAutorizada(),
                envio.getDniPersonaAutorizada(),
                envio.getSede() != null ? envio.getSede().getId() : null,
                envio.getSedeDestino() != null ? envio.getSedeDestino().getId() : null
        );
    }

    public void updateEntity(Envios envio, EnviosDTO dto) {
        envio.setNombreRemitente(dto.nombreRemitente());
        envio.setDniRemitente(dto.dniRemitente());
        envio.setNombreDestinatario(dto.nombreDestinatario());
        envio.setDniDestinatario(dto.dniDestinatario());
        envio.setHoraSalida(dto.horaSalida());
        envio.setHoraLlegada(dto.horaLlegada());
        envio.setFechaEnvio(dto.fechaEnvio());
        envio.setProvincia(dto.provincia());
        envio.setEstadoEnvio(dto.estadoEnvio());
    }
}
