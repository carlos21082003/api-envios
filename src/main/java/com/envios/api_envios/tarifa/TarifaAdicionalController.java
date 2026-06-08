package com.envios.api_envios.tarifa;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/tarifas")
@RequiredArgsConstructor
public class TarifaAdicionalController {
    private final TarifaAdicionalRepository tarifaRepository;

    @GetMapping("/vigente")
    public ResponseEntity<TarifaAdicional> getTarifaVigente() {
        return tarifaRepository.findTopBy()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
