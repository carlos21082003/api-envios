package com.envios.api_envios.rutas;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/rutas-sedes")
@RequiredArgsConstructor
public class RutaSedeController {
    private final RutaSedeService rutaSedeService;

    @PostMapping
    public ResponseEntity<RutaSedeDTO> crear(@RequestBody RutaSedeDTO dto) {
        return ResponseEntity.ok(rutaSedeService.crear(dto));
    }

    @GetMapping
    public ResponseEntity<Page<RutaSedeDTO>> listar(
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "15") int cantidad) {
        return ResponseEntity.ok(rutaSedeService.listar(pagina, cantidad));
    }

    // rutas disponibles desde una sede — útil para el formulario de envíos
    @GetMapping("/origen/{sedeOrigenId}")
    public ResponseEntity<List<RutaSedeDTO>> listarPorOrigen(
            @PathVariable Long sedeOrigenId) {
        return ResponseEntity.ok(rutaSedeService.listarPorOrigen(sedeOrigenId));
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<RutaSedeDTO> cambiarEstado(
            @PathVariable Long id,
            @RequestParam Boolean activo) {
        return ResponseEntity.ok(rutaSedeService.cambiarEstado(id, activo));
    }
}
