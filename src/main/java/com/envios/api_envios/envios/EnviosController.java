package com.envios.api_envios.envios;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/envios")
@RequiredArgsConstructor
public class EnviosController {

    private final EnviosService enviosService;

    // Crear envío
    @PostMapping("/guardarEnvio")
    public ResponseEntity<EnviosDTO> guardar(@RequestBody EnviosDTO enviosDTO) {
        return ResponseEntity.ok(enviosService.guardar(enviosDTO));
    }

    // Rastrear por DNI remitente
    @GetMapping("/rastrear/{dniRemitente}")
    public ResponseEntity<List<EnviosDTO>> getEnvioByDniRemitente(@PathVariable String dniRemitente) {
        return ResponseEntity.ok(enviosService.getEnviosByDniRemitente(dniRemitente));
    }

    // ✅ Rastrear por código de envío
    @GetMapping("/rastrear/codigo/{codigoEnvio}")
    public ResponseEntity<EnviosDTO> getEnvioByCodigo(@PathVariable String codigoEnvio) {
        return ResponseEntity.ok(enviosService.getEnvioByCodigo(codigoEnvio));
    }

    // Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<EnviosDTO> getEnvioById(@PathVariable Long id) {
        return ResponseEntity.ok(enviosService.getEnvioById(id));
    }

    // Listar con paginación
    @GetMapping
    public ResponseEntity<Page<EnviosDTO>> listarEnvios(
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "15") int cantidad,
            @RequestParam(required = false) Long sedeId) {
        return ResponseEntity.ok(enviosService.listarEnvios(pagina, cantidad, sedeId));
    }

    // Actualizar envío
    @PutMapping("/{id}")
    public ResponseEntity<EnviosDTO> actualizarEnvio(
            @PathVariable Long id,
            @RequestBody EnviosDTO enviosDTO) {
        return ResponseEntity.ok(enviosService.actualizarEnvio(id, enviosDTO));
    }

    // Mis envíos (usuario autenticado)
    @GetMapping("/mis-envios")
    public ResponseEntity<List<EnviosDTO>> getMisEnvios(Authentication authentication) {
        String dni = authentication.getName();
        return ResponseEntity.ok(enviosService.getEnviosByDniRemitente(dni));
    }
}