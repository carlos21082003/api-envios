package com.envios.api_envios.envios;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EnviosRepository extends JpaRepository<Envios,Long> {
    Optional<Envios> findByDniRemitente(String dniRemitente);
    Long countByEstadoEnvio(EstadoEnvio estadoEnvio);
}
