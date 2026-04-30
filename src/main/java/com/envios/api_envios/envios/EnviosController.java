package com.envios.api_envios.envios;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/envios")
@RequiredArgsConstructor

public class EnviosController {
    private final EnviosService enviosService;

    // Guardar envio
    @PostMapping("/guardarEnvio")
    public ResponseEntity<Envios> guardar(@RequestBody EnviosDTO enviosDTO) {
        Envios guardarEnvio = enviosService.guardar(enviosDTO);
        return ResponseEntity.ok(guardarEnvio);
    }

    // Buscar por DNI del remitente (código de rastreo)
    @GetMapping("/rastrear/{dniRemitente}")
    public ResponseEntity<EnviosDTO> getEnvioByDniRemitente(@PathVariable String dniRemitente) {
        return ResponseEntity.ok(enviosService.getEnvioByDniRemitente(dniRemitente));
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<EstadoEnvio> actualizarEstado(
            @PathVariable Long id,
            @RequestBody EnviosDTO enviosDTO) {
        return ResponseEntity.ok(enviosService.actualizarEstado(id, enviosDTO.estadoEnvio()));
    }
}
