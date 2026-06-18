package com.envios.api_envios.productos;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/productos")
@RequiredArgsConstructor
public class ProductosController {
    private final ProductosService productosService;

    //listar id
    @GetMapping("/{id}")
    public ResponseEntity<ProductosDTO> getProductoById(@PathVariable Long id) {
        return ResponseEntity.ok(productosService.getProductoById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductosDTO> actualizar(@PathVariable Long id, @Valid @RequestBody ProductosDTO productoDTO) {
        return ResponseEntity.ok(productosService.actualizar(id, productoDTO));
    }

    @GetMapping
    public ResponseEntity<Page<ProductosDTO>> listar(
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "15") int cantidad) {
        return ResponseEntity.ok(productosService.listar(pagina, cantidad));
    }
}
