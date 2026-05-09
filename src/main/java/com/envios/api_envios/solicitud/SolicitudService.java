package com.envios.api_envios.solicitud;

import com.envios.api_envios.envios.Envios;
import com.envios.api_envios.envios.EnviosRepository;
import com.envios.api_envios.sede.Sede;
import com.envios.api_envios.sede.SedeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class SolicitudService {
    private final SolicitudRepository solicitudRepository;
    private final SedeRepository sedeRepository;
    private final EnviosRepository enviosRepository;
    private final SolicitudMapper solicitudMapper;

    // cliente solicita recojo
    public SolicitudDTO solicitarRecojo(SolicitudDTO dto) {
        Sede sede = sedeRepository.findById(dto.sedeId())
                .orElseThrow(() -> new IllegalArgumentException("Sede no encontrada"));

        // verifica que la sede permita recojo
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

        solicitudRepository.save(solicitud);
        return solicitudMapper.toDTO(solicitud);
    }

    // cliente solicita delivery
    public SolicitudDTO solicitarDelivery(SolicitudDTO dto) {
        Sede sede = sedeRepository.findById(dto.sedeId())
                .orElseThrow(() -> new IllegalArgumentException("Sede no encontrada"));

        // verifica que la sede permita delivery
        if (!sede.getTieneDelivery()) {
            throw new IllegalArgumentException("Esta sede no tiene servicio de delivery");
        }

        // verifica que el envío exista
        Envios envio = enviosRepository.findById(dto.envioId())
                .orElseThrow(() -> new IllegalArgumentException("Envío no encontrado"));

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

    // empleado acepta la solicitud
    public SolicitudDTO aceptar(Long id) {
        SolicitudDomicilio solicitud = solicitudRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Solicitud no encontrada"));
        solicitud.setEstado(EstadoSolicitud.ACEPTADA);
        solicitudRepository.save(solicitud);
        return solicitudMapper.toDTO(solicitud);
    }

    // empleado rechaza la solicitud
    public SolicitudDTO rechazar(Long id, String motivo) {
        SolicitudDomicilio solicitud = solicitudRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Solicitud no encontrada"));
        solicitud.setEstado(EstadoSolicitud.RECHAZADA);
        solicitud.setMotivoRechazo(motivo);
        solicitudRepository.save(solicitud);
        return solicitudMapper.toDTO(solicitud);
    }

    // empleado completa la solicitud
    public SolicitudDTO completar(Long id) {
        SolicitudDomicilio solicitud = solicitudRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Solicitud no encontrada"));
        solicitud.setEstado(EstadoSolicitud.COMPLETADA);
        solicitud.setFechaAtencion(LocalDateTime.now());
        solicitudRepository.save(solicitud);
        return solicitudMapper.toDTO(solicitud);
    }

    // empleado lista solicitudes de su sede
    public Page<SolicitudDTO> listarPorSede(Long sedeId, int pagina, int cantidad) {
        Pageable pageable = PageRequest.of(pagina, cantidad);
        return solicitudRepository.findBySedeId(sedeId, pageable)
                .map(solicitudMapper::toDTO);
    }

    // filtrar por estado
    public Page<SolicitudDTO> listarPorSedeYEstado(Long sedeId, EstadoSolicitud estado, int pagina, int cantidad) {
        Pageable pageable = PageRequest.of(pagina, cantidad);
        return solicitudRepository.findBySedeIdAndEstado(sedeId, estado, pageable)
                .map(solicitudMapper::toDTO);
    }

    // cliente ve sus solicitudes por DNI
    public Page<SolicitudDTO> listarPorDni(String dni, int pagina, int cantidad) {
        Pageable pageable = PageRequest.of(pagina, cantidad);
        return solicitudRepository.findByDniSolicitante(dni, pageable)
                .map(solicitudMapper::toDTO);
    }
}
