package com.envios.api_envios.auditoria;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auditoria")
@RequiredArgsConstructor
public class AuditoriaController {
    private final AuditoriaService auditoriaService;

    @GetMapping
    public ResponseEntity<Page<Auditoria>> listar(
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "20") int cantidad) {
        return ResponseEntity.ok(auditoriaService.listar(pagina, cantidad));
    }

    @GetMapping("/errores")
    public ResponseEntity<Page<Auditoria>> listarErrores(
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "20") int cantidad) {
        return ResponseEntity.ok(auditoriaService.listarErrores(pagina, cantidad));
    }

    @GetMapping("/usuario/{dni}")
    public ResponseEntity<Page<Auditoria>> listarPorUsuario(
            @PathVariable String dni,
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "20") int cantidad) {
        return ResponseEntity.ok(auditoriaService.listarPorUsuario(dni, pagina, cantidad));
    }

    @GetMapping("/endpoint")
    public ResponseEntity<Page<Auditoria>> listarPorEndpoint(
            @RequestParam String endpoint,
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "20") int cantidad) {
        return ResponseEntity.ok(auditoriaService.listarPorEndpoint(endpoint, pagina, cantidad));
    }
}
