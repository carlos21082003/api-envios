package com.envios.api_envios.solicitud;

import com.envios.api_envios.envios.Envios;
import com.envios.api_envios.envios.EnviosRepository;
import com.envios.api_envios.sede.Sede;
import com.envios.api_envios.sede.SedeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SolicitudMapper {
    private final SedeRepository sedeRepository;
    private final EnviosRepository enviosRepository;

    public SolicitudDTO toDTO(SolicitudDomicilio solicitud) {
        return new SolicitudDTO(
                solicitud.getId(),
                solicitud.getTipo(),
                solicitud.getNombreSolicitante(),
                solicitud.getDniSolicitante(),
                solicitud.getTelefono(),
                solicitud.getDireccion(),
                solicitud.getReferencia(),
                solicitud.getDescripcionProducto(),
                solicitud.getFechaSolicitada(),
                solicitud.getFechaAtencion(),
                solicitud.getEstado(),
                solicitud.getMotivoRechazo(),
                solicitud.getSede() != null ? solicitud.getSede().getId() : null,
                solicitud.getSede() != null ? solicitud.getSede().getNombre() : null,
                solicitud.getEnvio() != null ? solicitud.getEnvio().getId() : null,
                solicitud.getEnvio() != null ? solicitud.getEnvio().getCodigoEnvio() : null,
                solicitud.getNombrePersonaRecibe(),
                solicitud.getDniPersonaRecibe(),
                solicitud.getSedeDestino() != null ? solicitud.getSedeDestino().getId() : null,
                solicitud.getSedeDestino() != null ? solicitud.getSedeDestino().getNombre() : null,
                solicitud.getNombreDestinatario(),
                solicitud.getDniDestinatario(),
                solicitud.getProvinciaDestino()
        );
    }

    public SolicitudDomicilio toEntity(SolicitudDTO dto) {
        SolicitudDomicilio solicitud = new SolicitudDomicilio();
        solicitud.setTipo(dto.tipo());
        solicitud.setNombreSolicitante(dto.nombreSolicitante());
        solicitud.setDniSolicitante(dto.dniSolicitante());
        solicitud.setTelefono(dto.telefono());
        solicitud.setDireccion(dto.direccion());
        solicitud.setReferencia(dto.referencia());
        solicitud.setDescripcionProducto(dto.descripcionProducto());
        solicitud.setEstado(EstadoSolicitud.PENDIENTE);
        solicitud.setNombrePersonaRecibe(dto.nombrePersonaRecibe());
        solicitud.setDniPersonaRecibe(dto.dniPersonaRecibe());
        // NUEVOS
        solicitud.setNombreDestinatario(dto.nombreDestinatario());
        solicitud.setDniDestinatario(dto.dniDestinatario());
        solicitud.setProvinciaDestino(dto.provinciaDestino());

        if (dto.sedeId() != null) {
            Sede sede = sedeRepository.findById(dto.sedeId())
                    .orElseThrow(() -> new IllegalArgumentException("Sede no encontrada"));
            solicitud.setSede(sede);
        }
        if (dto.envioId() != null) {
            Envios envio = enviosRepository.findById(dto.envioId())
                    .orElseThrow(() -> new IllegalArgumentException("Envío no encontrado"));
            solicitud.setEnvio(envio);
        }
        if (dto.sedeDestinoId() != null) {
            Sede sedeDestino = sedeRepository.findById(dto.sedeDestinoId())
                    .orElseThrow(() -> new IllegalArgumentException("Sede destino no encontrada"));
            solicitud.setSedeDestino(sedeDestino);
        }
        return solicitud;
    }

    public void updateEntity(SolicitudDomicilio solicitud, SolicitudDTO dto) {
        solicitud.setEstado(dto.estado());
        solicitud.setMotivoRechazo(dto.motivoRechazo());
        solicitud.setFechaAtencion(dto.fechaAtencion());
    }
}
