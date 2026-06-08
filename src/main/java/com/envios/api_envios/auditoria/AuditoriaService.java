package com.envios.api_envios.auditoria;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuditoriaService {
    private final AuditoriaRepository auditoriaRepository;

    public Page<Auditoria> listar(int pagina, int cantidad) {
        Pageable pageable = PageRequest.of(pagina, cantidad, Sort.by("fecha").descending());
        return auditoriaRepository.findAll(pageable);
    }

    public Page<Auditoria> listarErrores(int pagina, int cantidad) {
        Pageable pageable = PageRequest.of(pagina, cantidad, Sort.by("fecha").descending());
        return auditoriaRepository.findByStatusCodeGreaterThanEqual(400, pageable);
    }

    public Page<Auditoria> listarPorUsuario(String dni, int pagina, int cantidad) {
        Pageable pageable = PageRequest.of(pagina, cantidad, Sort.by("fecha").descending());
        return auditoriaRepository.findByDniUsuario(dni, pageable);
    }

    public Page<Auditoria> listarPorEndpoint(String endpoint, int pagina, int cantidad) {
        Pageable pageable = PageRequest.of(pagina, cantidad, Sort.by("fecha").descending());
        return auditoriaRepository.findByEndpointContaining(endpoint, pageable);
    }
}
