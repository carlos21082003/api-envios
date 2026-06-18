package com.envios.api_envios.envios;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.envios.api_envios.reportes.ProvinciasConteoDTO;

public interface EnviosRepository extends JpaRepository<Envios, Long> {

    @Query("SELECT e FROM Envios e WHERE e.dniRemitente = :dni ORDER BY e.fechaEnvio DESC")
    List<Envios> findByDniRemitenteOrderByFechaEnvioDesc(@Param("dni") String dni);

    Long countByEstadoEnvio(EstadoEnvio estadoEnvio);

    // ✅ Se eliminó findBySedeId — reemplazado por el query de abajo
    Page<Envios> findAll(Pageable pageable);

    @Query("SELECT new com.envios.api_envios.reportes.ProvinciasConteoDTO(e.provincia, COUNT(e)) " +
            "FROM Envios e GROUP BY e.provincia ORDER BY COUNT(e) DESC")
    List<ProvinciasConteoDTO> countByProvincia();

    // ✅ Busca por sede origen O sede destino
    @Query("SELECT e FROM Envios e WHERE e.sede.id = :sedeId OR e.sedeDestino.id = :sedeId")
    Page<Envios> findBySedeOrigenOrSedeDestino(@Param("sedeId") Long sedeId, Pageable pageable);

    Optional<Envios> findByCodigoEnvio(String codigoEnvio);

    boolean existsByCodigoEnvio(String codigoEnvio);
}