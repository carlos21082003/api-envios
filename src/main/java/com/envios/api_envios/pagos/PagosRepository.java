package com.envios.api_envios.pagos;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PagosRepository extends JpaRepository<Pagos,Long> {
    @Query("SELECT COALESCE(SUM(p.monto), 0) FROM Pagos p")
    Double sumMonto();

    @Query("SELECT COALESCE(SUM(p.monto), 0) FROM Pagos p WHERE p.metodoPago = :metodoPago")
    Double sumMontoByMetodoPago(@Param("metodoPago") MetodoPago metodoPago);

    Long countByEstadoPago(EstadoPago estadoPago);

    @Query("SELECT COALESCE(SUM(pr.numeroPaquetes), 0) FROM Productos pr")
    Double sumTotalPaquetes();

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT p FROM Pagos p WHERE p.id = :id")
    Optional<Pagos> findByIdWithLock(@Param("id") Long id);
}
