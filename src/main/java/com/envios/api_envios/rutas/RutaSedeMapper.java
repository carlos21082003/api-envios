package com.envios.api_envios.rutas;

import com.envios.api_envios.sede.Sede;
import com.envios.api_envios.sede.SedeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RutaSedeMapper {
    private final SedeRepository sedeRepository;

    public RutaSedeDTO toDTO(RutaSede ruta) {
        return new RutaSedeDTO(
                ruta.getId(),
                ruta.getSedeOrigen().getId(),
                ruta.getSedeOrigen().getNombre(),
                ruta.getSedeDestino().getId(),
                ruta.getSedeDestino().getNombre(),
                ruta.getActivo()
        );
    }

    public RutaSede toEntity(RutaSedeDTO dto) {
        Sede origen = sedeRepository.findById(dto.sedeOrigenId())
                .orElseThrow(() -> new IllegalArgumentException("Sede origen no encontrada"));
        Sede destino = sedeRepository.findById(dto.sedeDestinoId())
                .orElseThrow(() -> new IllegalArgumentException("Sede destino no encontrada"));

        RutaSede ruta = new RutaSede();
        ruta.setSedeOrigen(origen);
        ruta.setSedeDestino(destino);
        ruta.setActivo(true);
        return ruta;
    }
}
