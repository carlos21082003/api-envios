package com.envios.api_envios.rutas;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RutaSedeService {
    private final RutaSedeRepository rutaSedeRepository;
    private final RutaSedeMapper rutaSedeMapper;

    // crear ruta entre dos sedes
    public RutaSedeDTO crear(RutaSedeDTO dto) {
        // verifica que no exista ya esa ruta
        rutaSedeRepository
                .findBySedeOrigenIdAndSedeDestinoIdAndActivoTrue(
                        dto.sedeOrigenId(), dto.sedeDestinoId())
                .ifPresent(r -> {
                    throw new IllegalArgumentException("Ya existe una ruta activa entre estas sedes");
                });

        RutaSede ruta = rutaSedeMapper.toEntity(dto);
        rutaSedeRepository.save(ruta);
        return rutaSedeMapper.toDTO(ruta);
    }

    // listar todas paginado
    public Page<RutaSedeDTO> listar(int pagina, int cantidad) {
        Pageable pageable = PageRequest.of(pagina, cantidad);
        return rutaSedeRepository.findAll(pageable)
                .map(rutaSedeMapper::toDTO);
    }

    // listar rutas disponibles desde una sede
    public List<RutaSedeDTO> listarPorOrigen(Long sedeOrigenId) {
        return rutaSedeRepository
                .findBySedeOrigenIdAndActivoTrue(sedeOrigenId)
                .stream()
                .map(rutaSedeMapper::toDTO)
                .toList();
    }

    // activar o desactivar ruta
    public RutaSedeDTO cambiarEstado(Long id, Boolean activo) {
        RutaSede ruta = rutaSedeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Ruta no encontrada"));
        ruta.setActivo(activo);
        rutaSedeRepository.save(ruta);
        return rutaSedeMapper.toDTO(ruta);
    }

    // validar si existe ruta activa entre dos sedes
    // este metodo lo usan otros servicios como EnviosService
    public boolean existeRuta(Long sedeOrigenId, Long sedeDestinoId) {
        return rutaSedeRepository
                .findBySedeOrigenIdAndSedeDestinoIdAndActivoTrue(
                        sedeOrigenId, sedeDestinoId)
                .isPresent();
    }
}
