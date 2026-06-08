package com.envios.api_envios.usuarios;

import com.envios.api_envios.jwt.TokenDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/usuarios")
@RequiredArgsConstructor
public class UsuariosController {
    private final UsuariosService usuarioService;

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@RequestBody LoginDTO dto) {
        return ResponseEntity.ok(usuarioService.login(dto));
    }

    @PostMapping
    public ResponseEntity<UsuariosDTO> crear(@RequestBody UsuariosDTO dto) {
        return ResponseEntity.ok(usuarioService.crear(dto));
    }

    @GetMapping
    public ResponseEntity<Page<UsuariosDTO>> listar(
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "15") int cantidad) {
        return ResponseEntity.ok(usuarioService.listar(pagina, cantidad));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuariosDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuariosDTO> actualizar(
            @PathVariable Long id,
            @RequestBody UsuariosDTO dto) {
        return ResponseEntity.ok(usuarioService.actualizar(id, dto));
    }

    @PatchMapping("/{id}/password")
    public ResponseEntity<UsuariosDTO> cambiarPassword(
            @PathVariable Long id,
            @RequestBody String nuevaPassword) {
        return ResponseEntity.ok(usuarioService.cambiarPassword(id, nuevaPassword));
    }

    @GetMapping("/me")
    public ResponseEntity<UsuariosDTO> getMe() {
        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();
        if (authentication == null) {
            throw new IllegalStateException("No existe una sesión autenticada");
        }
        String nombre = authentication.getName();
        return ResponseEntity.ok(usuarioService.getByDni(nombre));
    }
}
