package com.envios.api_envios.usuarios;

import com.envios.api_envios.sede.Sede;
import com.envios.api_envios.sede.SedeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UsuarioMapper {
    private final SedeRepository sedeRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UsuariosDTO toDTO(Usuarios usuario) {
        return new UsuariosDTO(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getDni(),
                null,
                usuario.getTelefono(),
                usuario.getRol(),
                usuario.getSede() != null ? usuario.getSede().getId() : null,
                usuario.getSede() != null ? usuario.getSede().getNombre() : null,
                usuario.getActivo()
        );
    }

    public Usuarios toEntity(UsuariosDTO dto) {
        Usuarios usuario = new Usuarios();
        usuario.setNombre(dto.nombre());
        usuario.setDni(dto.dni());
        usuario.setTelefono(dto.telefono());
        usuario.setPassword(passwordEncoder.encode(dto.password()));
        usuario.setRol(dto.rol());
        usuario.setActivo(true);

        if (dto.sedeId() != null) {
            Sede sede = sedeRepository.findById(dto.sedeId())
                    .orElseThrow(() -> new IllegalArgumentException("Sede no encontrada"));
            usuario.setSede(sede);
        }
        return usuario;
    }

    public void updateEntity(Usuarios usuario, UsuariosDTO dto) {
        usuario.setNombre(dto.nombre());
        usuario.setDni(dto.dni());
        usuario.setRol(dto.rol());
        usuario.setTelefono(dto.telefono());
        usuario.setActivo(dto.activo());

        if (dto.sedeId() != null) {
            Sede sede = sedeRepository.findById(dto.sedeId())
                    .orElseThrow(() -> new IllegalArgumentException("Sede no encontrada"));
            usuario.setSede(sede);
        }
    }
}
