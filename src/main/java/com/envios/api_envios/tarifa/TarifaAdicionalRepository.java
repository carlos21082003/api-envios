package com.envios.api_envios.tarifa;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TarifaAdicionalRepository extends JpaRepository<TarifaAdicional, Long> {
    Optional<TarifaAdicional> findTopBy();
}
