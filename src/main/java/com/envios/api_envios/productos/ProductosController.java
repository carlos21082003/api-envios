package com.envios.api_envios.productos;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/productos")
@RequiredArgsConstructor
public class ProductosController {
    private final ProductosService productosService;

    //guardar productos
    @PostMapping("/guardar")
    public ResponseEntity<Productos> guardar(@RequestBody ProductosDTO productoDTO) {
        Productos guardarProductos = productosService.guardar(productoDTO);
        return ResponseEntity.ok(guardarProductos);
    }

    //listar id
    @GetMapping("/{id}")
    public ResponseEntity<ProductosDTO> getProductoById(@PathVariable Long id) {
        return ResponseEntity.ok(productosService.getProductoById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductosDTO> actualizar(@PathVariable Long id, @RequestBody ProductosDTO productoDTO) {
        return ResponseEntity.ok(productosService.actualizar(id, productoDTO));
    }
}
