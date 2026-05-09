package com.envios.api_envios.solicitud;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SolicitudRepository extends JpaRepository<SolicitudDomicilio, Long> {
    Page<SolicitudDomicilio> findBySedeId(Long sedeId, Pageable pageable);
    Page<SolicitudDomicilio> findBySedeIdAndEstado(Long sedeId, EstadoSolicitud estado, Pageable pageable);
    Page<SolicitudDomicilio> findByDniSolicitante(String dni, Pageable pageable);
}
