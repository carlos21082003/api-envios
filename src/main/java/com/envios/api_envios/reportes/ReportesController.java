package com.envios.api_envios.reportes;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/reportes")
@RequiredArgsConstructor
public class ReportesController {
    private final ReportesService reportesService;

    @GetMapping
    public ResponseEntity<ReportesDTO> getReporte() {
        return ResponseEntity.ok(reportesService.getReporte());
    }
}
