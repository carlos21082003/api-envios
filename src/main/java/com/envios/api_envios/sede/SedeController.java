package com.envios.api_envios.sede;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/sedes")
@RequiredArgsConstructor
public class SedeController {
    private final SedeService sedeService;

    @PostMapping
    public ResponseEntity<SedeDTO> guardar(@RequestBody SedeDTO dto) {
        return ResponseEntity.ok(sedeService.guardar(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SedeDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(sedeService.getById(id));
    }

    // lista paginada de activas
    @GetMapping
    public ResponseEntity<Page<SedeDTO>> listar(
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "15") int cantidad) {
        return ResponseEntity.ok(sedeService.listar(pagina, cantidad));
    }

    // lista todas incluyendo inactivas
    @GetMapping("/todas")
    public ResponseEntity<Page<SedeDTO>> listarTodas(
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "15") int cantidad) {
        return ResponseEntity.ok(sedeService.listarTodas(pagina, cantidad));
    }

    // lista simple para selects
    @GetMapping("/activas")
    public ResponseEntity<List<SedeDTO>> listarActivas() {
        return ResponseEntity.ok(sedeService.listarActivas());
    }

    @PutMapping("/{id}")
    public ResponseEntity<SedeDTO> actualizar(
            @PathVariable Long id,
            @RequestBody SedeDTO dto) {
        return ResponseEntity.ok(sedeService.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SedeDTO> desactivar(@PathVariable Long id) {
        return ResponseEntity.ok(sedeService.desactivar(id));
    }
}
