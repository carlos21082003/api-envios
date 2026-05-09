package com.envios.api_envios.usuarios;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UsuariosRepository extends JpaRepository<Usuarios, Long> {
    Optional<Usuarios> findByDni(String dni);
    boolean existsByDni(String dni);
    Page<Usuarios> findBySedeId(Long sedeId, Pageable pageable);
    Page<Usuarios> findByRol(Roles rol, Pageable pageable);
}
