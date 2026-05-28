package com.envios.api_envios.solicitud;

import com.envios.api_envios.envios.*;
import com.envios.api_envios.pagos.PagosDTO;
import com.envios.api_envios.productos.ProductosDTO;
import com.envios.api_envios.sede.Sede;
import com.envios.api_envios.sede.SedeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SolicitudService {
    private final SolicitudRepository solicitudRepository;
    private final SedeRepository sedeRepository;
    private final EnviosRepository enviosRepository;
    private final SolicitudMapper solicitudMapper;
    private final EnviosService enviosService;

    public SolicitudDTO solicitarRecojo(SolicitudDTO dto) {
        Sede sede = sedeRepository.findById(dto.sedeId())
                .orElseThrow(() -> new IllegalArgumentException("Sede no encontrada"));

        if (!sede.getTieneRecojo()) {
            throw new IllegalArgumentException("Esta sede no tiene servicio de recojo a domicilio");
        }

        SolicitudDomicilio solicitud = new SolicitudDomicilio();
        solicitud.setTipo(TipoSolicitud.RECOJO);
        solicitud.setNombreSolicitante(dto.nombreSolicitante());
        solicitud.setDniSolicitante(dto.dniSolicitante());
        solicitud.setTelefono(dto.telefono());
        solicitud.setDireccion(dto.direccion());
        solicitud.setReferencia(dto.referencia());
        solicitud.setDescripcionProducto(dto.descripcionProducto());
        solicitud.setFechaSolicitada(LocalDateTime.now());
        solicitud.setEstado(EstadoSolicitud.PENDIENTE);
        solicitud.setSede(sede);

        // CORREGIDO — ahora sí se guardan
        solicitud.setNombrePersonaRecibe(dto.nombrePersonaRecibe());
        solicitud.setDniPersonaRecibe(dto.dniPersonaRecibe());

        // NUEVO — destinatario del envío
        solicitud.setNombreDestinatario(dto.nombreDestinatario());
        solicitud.setDniDestinatario(dto.dniDestinatario());
        solicitud.setProvinciaDestino(dto.provinciaDestino());

        if (dto.sedeDestinoId() != null) {
            Sede sedeDestino = sedeRepository.findById(dto.sedeDestinoId())
                    .orElseThrow(() -> new IllegalArgumentException("Sede destino no encontrada"));
            solicitud.setSedeDestino(sedeDestino);
        }

        solicitudRepository.save(solicitud);
        return solicitudMapper.toDTO(solicitud);
    }

    public SolicitudDTO solicitarDelivery(SolicitudDTO dto) {
        Sede sede = sedeRepository.findById(dto.sedeId())
                .orElseThrow(() -> new IllegalArgumentException("Sede no encontrada"));

        if (!sede.getTieneDelivery()) {
            throw new IllegalArgumentException("Esta sede no tiene servicio de delivery");
        }

        Envios envio = enviosRepository.findByCodigoEnvio(dto.codigoEnvio())
                .orElseThrow(() -> new IllegalArgumentException("No se encontró un envío con ese código"));

        SolicitudDomicilio solicitud = new SolicitudDomicilio();
        solicitud.setTipo(TipoSolicitud.DELIVERY);
        solicitud.setNombreSolicitante(dto.nombreSolicitante());
        solicitud.setDniSolicitante(dto.dniSolicitante());
        solicitud.setTelefono(dto.telefono());
        solicitud.setDireccion(dto.direccion());
        solicitud.setReferencia(dto.referencia());
        solicitud.setFechaSolicitada(LocalDateTime.now());
        solicitud.setEstado(EstadoSolicitud.PENDIENTE);
        solicitud.setSede(sede);
        solicitud.setEnvio(envio);

        solicitudRepository.save(solicitud);
        return solicitudMapper.toDTO(solicitud);
    }

    public SolicitudDTO aceptar(Long id) {
        SolicitudDomicilio solicitud = solicitudRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Solicitud no encontrada"));
        solicitud.setEstado(EstadoSolicitud.ACEPTADA);
        solicitudRepository.save(solicitud);
        return solicitudMapper.toDTO(solicitud);
    }

    public SolicitudDTO rechazar(Long id, String motivo) {
        SolicitudDomicilio solicitud = solicitudRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Solicitud no encontrada"));
        solicitud.setEstado(EstadoSolicitud.RECHAZADA);
        solicitud.setMotivoRechazo(motivo);
        solicitudRepository.save(solicitud);
        return solicitudMapper.toDTO(solicitud);
    }

    public SolicitudDTO completar(Long id, CompletarSolicitudDTO completarDTO) {
        SolicitudDomicilio solicitud = solicitudRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Solicitud no encontrada"));

        solicitud.setEstado(EstadoSolicitud.COMPLETADA);
        solicitud.setFechaAtencion(LocalDateTime.now());
        solicitudRepository.save(solicitud);

        if (solicitud.getTipo() == TipoSolicitud.RECOJO) {

            ProductosDTO productoDTO = new ProductosDTO(
                    null,
                    completarDTO.tipoProductoId(),
                    null,
                    solicitud.getDescripcionProducto(),
                    completarDTO.numeroPaquetes(),
                    null
            );

            PagosDTO pagoDTO = new PagosDTO(
                    null,
                    null,
                    completarDTO.metodoPago(),
                    LocalDateTime.now(),
                    completarDTO.estadoPago()
            );

            EnviosDTO envioDTO = new EnviosDTO(
                    null,
                    completarDTO.horaSalida(),
                    completarDTO.horaLlegada(),
                    null,
                    LocalDateTime.now(),
                    solicitud.getNombreDestinatario(),
                    solicitud.getDniDestinatario(),
                    solicitud.getNombreSolicitante(),
                    solicitud.getDniSolicitante(),
                    EstadoEnvio.PORSALIR,
                    pagoDTO,
                    List.of(productoDTO),
                    solicitud.getProvinciaDestino(),
                    solicitud.getSede() != null ? solicitud.getSede().getId() : null,
                    null,
                    solicitud.getNombrePersonaRecibe(),
                    solicitud.getDniPersonaRecibe(),
                    solicitud.getSede() != null ? solicitud.getSede().getId() : null,
                    solicitud.getSedeDestino() != null ? solicitud.getSedeDestino().getId() : null
            );

            enviosService.guardar(envioDTO);
        }

        return solicitudMapper.toDTO(solicitud);
    }

    public Page<SolicitudDTO> listarPorSede(Long sedeId, int pagina, int cantidad) {
        Pageable pageable = PageRequest.of(pagina, cantidad);
        return solicitudRepository.findBySedeId(sedeId, pageable)
                .map(solicitudMapper::toDTO);
    }

    public Page<SolicitudDTO> listarPorSedeYEstado(Long sedeId, EstadoSolicitud estado, int pagina, int cantidad) {
        Pageable pageable = PageRequest.of(pagina, cantidad);
        return solicitudRepository.findBySedeIdAndEstado(sedeId, estado, pageable)
                .map(solicitudMapper::toDTO);
    }

    public Page<SolicitudDTO> listarPorDni(String dni, int pagina, int cantidad) {
        Pageable pageable = PageRequest.of(pagina, cantidad);
        return solicitudRepository.findByDniSolicitante(dni, pageable)
                .map(solicitudMapper::toDTO);
    }
}