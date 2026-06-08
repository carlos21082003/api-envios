package com.envios.api_envios.auditoria;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditoriaRepository extends JpaRepository<Auditoria, Long> {
    Page<Auditoria> findByDniUsuario(String dni, Pageable pageable);
    Page<Auditoria> findByStatusCodeGreaterThanEqual(Integer code, Pageable pageable);
    Page<Auditoria> findByEndpointContaining(String endpoint, Pageable pageable);
}
