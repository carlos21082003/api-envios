package com.envios.api_envios.pagos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PagosRepository extends JpaRepository<Pagos,Long> {
    @Query("SELECT COALESCE(SUM(p.monto), 0) FROM Pagos p")
    Double sumMonto();

    @Query("SELECT COALESCE(SUM(p.monto), 0) FROM Pagos p WHERE p.metodoPago = :metodoPago")
    Double sumMontoByMetodoPago(@Param("metodoPago") String metodoPago);

    Long countByEstadoPago(EstadoPago estadoPago);

    @Query("SELECT COALESCE(SUM(pr.numeroPaquetes), 0) FROM Productos pr")
    Double sumTotalPaquetes();
}
