package com.envios.api_envios.usuarios;

import com.envios.api_envios.jwt.JwtService;
import com.envios.api_envios.jwt.TokenDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuariosService implements UserDetailsService {
    private final UsuariosRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;
    private final JwtService jwtService;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String dni) throws UsernameNotFoundException {
        return usuarioRepository.findByDni(dni)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + dni));
    }

    public TokenDTO login(LoginDTO dto) {
        Usuarios usuario = usuarioRepository.findByDni(dto.dni())
                .orElseThrow(() -> new IllegalArgumentException("Credenciales incorrectas"));

        if (!passwordEncoder.matches(dto.password(), usuario.getPassword())) {
            throw new IllegalArgumentException("Credenciales incorrectas");
        }
        if (!usuario.getActivo()) {
            throw new IllegalArgumentException("Usuario inactivo");
        }

        String token = jwtService.generarToken(usuario);
        return new TokenDTO(
                token,
                usuario.getDni(),
                usuario.getRol().name(),
                usuario.getNombre(),
                usuario.getSede() != null ? usuario.getSede().getId() : null,
                usuario.getSede() != null ? usuario.getSede().getNombre() : null
        );
    }

    public UsuariosDTO crear(UsuariosDTO dto) {
        if (usuarioRepository.existsByDni(dto.dni())) {
            throw new IllegalArgumentException("El DNI ya está registrado");
        }
        Usuarios usuario = usuarioMapper.toEntity(dto);
        usuarioRepository.save(usuario);
        return usuarioMapper.toDTO(usuario);
    }

    public UsuariosDTO actualizar(Long id, UsuariosDTO dto) {
        Usuarios usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        usuarioMapper.updateEntity(usuario, dto);
        usuarioRepository.save(usuario);
        return usuarioMapper.toDTO(usuario);
    }

    public UsuariosDTO cambiarPassword(Long id, String nuevaPassword) {
        Usuarios usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        usuario.setPassword(passwordEncoder.encode(nuevaPassword));
        usuarioRepository.save(usuario);
        return usuarioMapper.toDTO(usuario);
    }

    public UsuariosDTO getById(Long id) {
        return usuarioMapper.toDTO(
                usuarioRepository.findById(id)
                        .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"))
        );
    }

    public Page<UsuariosDTO> listar(int pagina, int cantidad) {
        Pageable pageable = PageRequest.of(pagina, cantidad);
        return usuarioRepository.findAll(pageable)
                .map(usuarioMapper::toDTO);
    }

    public Page<UsuariosDTO> listarPorSede(Long sedeId, int pagina, int cantidad) {
        Pageable pageable = PageRequest.of(pagina, cantidad);
        return usuarioRepository.findBySedeId(sedeId, pageable)
                .map(usuarioMapper::toDTO);
    }

    public UsuariosDTO getByDni(String dni) {
        return usuarioMapper.toDTO(
                usuarioRepository.findByDni(dni)
                        .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"))
        );
    }
}
