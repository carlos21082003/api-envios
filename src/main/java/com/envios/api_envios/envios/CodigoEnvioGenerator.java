package com.envios.api_envios.envios;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
@RequiredArgsConstructor
public class CodigoEnvioGenerator {
    private final EnviosRepository enviosRepository;

    public String generar() {
        String codigo;
        do {
            codigo = String.format("%06d", new Random().nextInt(1_000_000));
        } while (enviosRepository.existsByCodigoEnvio(codigo));
        return codigo;
    }
}
