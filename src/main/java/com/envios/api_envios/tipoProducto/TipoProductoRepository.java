package com.envios.api_envios.tipoProducto;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;

@Repository
public interface TipoProductoRepository extends JpaRepository<TipoProducto,Long> {
    Page<TipoProducto> findByActivoTrue(Pageable pageable);
}
