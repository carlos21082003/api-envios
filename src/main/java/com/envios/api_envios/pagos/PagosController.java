package com.envios.api_envios.pagos;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/pagos")
@RequiredArgsConstructor
public class PagosController {
    private final PagosService pagosService;

    //guardar
    @PostMapping("/guardarPago")
    public ResponseEntity<Pagos> guardar(@RequestBody PagosDTO pagosDTO) {
        Pagos guardarPagos = pagosService.guardar(pagosDTO);
        return ResponseEntity.ok(guardarPagos);
    }

    //listarID
    @GetMapping("/{id}")
    public ResponseEntity<PagosDTO> getPagosById(@PathVariable Long id) {
        return ResponseEntity.ok(pagosService.getPagoById(id));
    }
}
