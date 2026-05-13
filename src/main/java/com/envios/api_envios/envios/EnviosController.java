package com.envios.api_envios.envios;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/envios")
@RequiredArgsConstructor

public class EnviosController {
    private final EnviosService enviosService;

    // Guardar envio
    @PostMapping("/guardarEnvio")
    public ResponseEntity<EnviosDTO> guardar(@RequestBody EnviosDTO enviosDTO) {
        return ResponseEntity.ok(enviosService.guardar(enviosDTO));  // EnviosDTO no Envios
    }

    // Buscar por DNI del remitente (código de rastreo)
    @GetMapping("/rastrear/{dniRemitente}")
    public ResponseEntity<EnviosDTO> getEnvioByDniRemitente(@PathVariable String dniRemitente) {
        return ResponseEntity.ok(enviosService.getEnvioByDniRemitente(dniRemitente));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EnviosDTO> getEnvioById(@PathVariable Long id) {
        return ResponseEntity.ok(enviosService.getEnvioById(id));
    }

    //listar con paginacion
    @GetMapping
    public ResponseEntity<Page<EnviosDTO>> listarEnvios(
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "15") int cantidad,
            @RequestParam(required = false) Long sedeId) {

        return ResponseEntity.ok(enviosService.listarEnvios(pagina, cantidad, sedeId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EnviosDTO> actualizarEnvio(
            @PathVariable Long id,
            @RequestBody EnviosDTO enviosDTO) {
        return ResponseEntity.ok(enviosService.actualizarEnvio(id, enviosDTO));
    }
}
