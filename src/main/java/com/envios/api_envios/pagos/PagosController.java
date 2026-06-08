package com.envios.api_envios.pagos;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/pagos")
@RequiredArgsConstructor
public class PagosController {
    private final PagosService pagosService;

    //listarID
    @GetMapping("/{id}")
    public ResponseEntity<PagosDTO> getPagosById(@PathVariable Long id) {
        return ResponseEntity.ok(pagosService.getPagoById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PagosDTO> actualizar(
            @PathVariable Long id,
            @RequestBody PagosDTO pagosDTO) {
        return ResponseEntity.ok(pagosService.actualizarEstado(id, pagosDTO));
    }

    @GetMapping
    public ResponseEntity<Page<PagosDTO>> listarPagos(
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "15") int cantidad) {
        return ResponseEntity.ok(pagosService.listarPagos(pagina, cantidad));
    }

    @PostMapping("/envio/{envioId}/pagar-en-linea")
    public ResponseEntity<PagosDTO> pagarEnLinea(
            @PathVariable Long envioId,
            @RequestBody PagoEnLineaDTO pagoDTO) {
        return ResponseEntity.ok(pagosService.pagarEnLinea(envioId));
    }
}
