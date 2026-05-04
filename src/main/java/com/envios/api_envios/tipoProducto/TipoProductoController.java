package com.envios.api_envios.tipoProducto;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/tipo-productos")
@RequiredArgsConstructor
public class TipoProductoController {
    private final TipoProductoService tipoProductoService;

    @GetMapping("/{id}")
    public ResponseEntity<TipoProductoDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(tipoProductoService.getById(id));
    }

    @PostMapping("/guardar")
    public ResponseEntity<TipoProductoDTO> guardar(@RequestBody TipoProductoDTO dto) {
        return ResponseEntity.ok(tipoProductoService.guardar(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TipoProductoDTO> actualizar(
            @PathVariable Long id,
            @RequestBody TipoProductoDTO dto) {
        return ResponseEntity.ok(tipoProductoService.actualizar(id, dto));
    }

    @GetMapping
    public ResponseEntity<Page<TipoProductoDTO>> listar(
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "15") int cantidad) {
        return ResponseEntity.ok(tipoProductoService.listar(pagina, cantidad));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        tipoProductoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
