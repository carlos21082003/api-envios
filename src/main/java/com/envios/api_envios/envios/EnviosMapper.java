package com.envios.api_envios.envios;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.envios.api_envios.pagos.PagosMapper;
import com.envios.api_envios.productos.Productos;
import com.envios.api_envios.productos.ProductosMapper;
import com.envios.api_envios.sede.Sede;
import com.envios.api_envios.sede.SedeRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class EnviosMapper {

    private final PagosMapper pagosMapper;
    private final ProductosMapper productosMapper;
    private final SedeRepository sedeRepository;

    public Envios toEntity(EnviosDTO dto) {
        Envios envio = new Envios();
        envio.setPago(pagosMapper.toEntity(dto.pago()));

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

        // ✅ Sede origen: acepta sedeOrigenId o sedeId
        Long sedeOrigenId = dto.sedeOrigenId() != null ? dto.sedeOrigenId() : dto.sedeId();
        if (sedeOrigenId != null) {
            Sede sede = sedeRepository.findById(sedeOrigenId)
                    .orElseThrow(() -> new IllegalArgumentException("Sede origen no encontrada"));
            envio.setSede(sede);
        }

        // ✅ Sede destino
        if (dto.sedeDestinoId() != null) {
            Sede sedeDestino = sedeRepository.findById(dto.sedeDestinoId())
                    .orElseThrow(() -> new IllegalArgumentException("Sede destino no encontrada"));
            envio.setSedeDestino(sedeDestino);
        }

        return envio;
    }

    public EnviosDTO toDTO(Envios envio) {
        // ✅ Variables intermedias para evitar duplicación
        Long sedeOrigenId = envio.getSede() != null ? envio.getSede().getId() : null;
        String sedeNombre = envio.getSede() != null ? envio.getSede().getNombre() : null;
        Long sedeDestinoId = envio.getSedeDestino() != null ? envio.getSedeDestino().getId() : null;

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
                sedeOrigenId,   // sedeId
                sedeNombre,     // sedeNombre
                envio.getNombrePersonaAutorizada(),
                envio.getDniPersonaAutorizada(),
                sedeOrigenId,   // ✅ sedeOrigenId
                sedeDestinoId   // ✅ sedeDestinoId
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

        // ✅ Actualizar sedes al editar
        Long sedeOrigenId = dto.sedeOrigenId() != null ? dto.sedeOrigenId() : dto.sedeId();
        if (sedeOrigenId != null) {
            Sede sede = sedeRepository.findById(sedeOrigenId)
                    .orElseThrow(() -> new IllegalArgumentException("Sede origen no encontrada"));
            envio.setSede(sede);
        }
        if (dto.sedeDestinoId() != null) {
            Sede sedeDestino = sedeRepository.findById(dto.sedeDestinoId())
                    .orElseThrow(() -> new IllegalArgumentException("Sede destino no encontrada"));
            envio.setSedeDestino(sedeDestino);
        }
    }
}