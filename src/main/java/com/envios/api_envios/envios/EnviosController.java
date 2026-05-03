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
    public ResponseEntity<Envios> guardar(@RequestBody EnviosDTO enviosDTO) {
        Envios guardarEnvio = enviosService.guardar(enviosDTO);
        return ResponseEntity.ok(guardarEnvio);
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

    @PutMapping("/{id}/estado")
    public ResponseEntity<EstadoEnvio> actualizarEstado(
            @PathVariable Long id,
            @RequestBody EnviosDTO enviosDTO) {
        return ResponseEntity.ok(enviosService.actualizarEstado(id, enviosDTO.estadoEnvio()));
    }

    //listar con paginacion
    @GetMapping
    public ResponseEntity<Page<EnviosDTO>> listarEnvios(
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "15") int cantidad) {

        return ResponseEntity.ok(enviosService.listarEnvios(pagina, cantidad));
    }
}
