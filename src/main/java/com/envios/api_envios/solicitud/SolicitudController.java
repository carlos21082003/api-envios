package com.envios.api_envios.solicitud;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/solicitudes")
@RequiredArgsConstructor
public class SolicitudController {
    private final SolicitudService solicitudService;

    // cliente solicita recojo
    @PostMapping("/recojo")
    public ResponseEntity<SolicitudDTO> solicitarRecojo(@RequestBody SolicitudDTO dto) {
        return ResponseEntity.ok(solicitudService.solicitarRecojo(dto));
    }

    // cliente solicita delivery
    @PostMapping("/delivery")
    public ResponseEntity<SolicitudDTO> solicitarDelivery(@RequestBody SolicitudDTO dto) {
        return ResponseEntity.ok(solicitudService.solicitarDelivery(dto));
    }

    // empleado acepta
    @PatchMapping("/{id}/aceptar")
    public ResponseEntity<SolicitudDTO> aceptar(@PathVariable Long id) {
        return ResponseEntity.ok(solicitudService.aceptar(id));
    }

    // empleado rechaza
    @PatchMapping("/{id}/rechazar")
    public ResponseEntity<SolicitudDTO> rechazar(
            @PathVariable Long id,
            @RequestBody String motivo) {
        return ResponseEntity.ok(solicitudService.rechazar(id, motivo));
    }

    // empleado completa
    @PatchMapping("/{id}/completar")
    public ResponseEntity<SolicitudDTO> completar(
            @PathVariable Long id,
            @RequestBody(required = false) CompletarSolicitudDTO dto) {
        return ResponseEntity.ok(solicitudService.completar(id, dto));
    }

    // empleado lista por sede
    @GetMapping("/sede/{sedeId}")
    public ResponseEntity<Page<SolicitudDTO>> listarPorSede(
            @PathVariable Long sedeId,
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "15") int cantidad) {
        return ResponseEntity.ok(solicitudService.listarPorSede(sedeId, pagina, cantidad));
    }

    // filtrar por estado
    @GetMapping("/sede/{sedeId}/estado/{estado}")
    public ResponseEntity<Page<SolicitudDTO>> listarPorEstado(
            @PathVariable Long sedeId,
            @PathVariable EstadoSolicitud estado,
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "15") int cantidad) {
        return ResponseEntity.ok(solicitudService.listarPorSedeYEstado(sedeId, estado, pagina, cantidad));
    }

    // cliente ve sus solicitudes
    @GetMapping("/cliente/{dni}")
    public ResponseEntity<Page<SolicitudDTO>> listarPorDni(
            @PathVariable String dni,
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "15") int cantidad) {
        return ResponseEntity.ok(solicitudService.listarPorDni(dni, pagina, cantidad));
    }


}
