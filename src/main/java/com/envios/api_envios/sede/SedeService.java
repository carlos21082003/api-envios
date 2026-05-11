package com.envios.api_envios.sede;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SedeService {
    private final SedeRepository sedeRepository;
    private final SedeMapper sedeMapper;

    public SedeDTO getById(Long id) {
        Sede sede = sedeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Sede no encontrada"));
        return sedeMapper.toDTO(sede);
    }

    public SedeDTO guardar(SedeDTO dto) {
        Sede sede = sedeMapper.toEntity(dto);
        sedeRepository.save(sede);
        return sedeMapper.toDTO(sede);
    }

    public SedeDTO actualizar(Long id, SedeDTO dto) {
        Sede sede = sedeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Sede no encontrada"));
        sedeMapper.updateEntity(sede, dto);
        sedeRepository.save(sede);
        return sedeMapper.toDTO(sede);
    }

    // listar solo activas con paginacion
    public Page<SedeDTO> listar(int pagina, int cantidad) {
        Pageable pageable = PageRequest.of(pagina, cantidad);
        return sedeRepository.findByActivoTrue(pageable)
                .map(sedeMapper::toDTO);
    }

    // listar todas incluyendo inactivas (para SUPER_ADMIN)
    public Page<SedeDTO> listarTodas(int pagina, int cantidad) {
        Pageable pageable = PageRequest.of(pagina, cantidad);
        return sedeRepository.findAll(pageable)
                .map(sedeMapper::toDTO);
    }

    // lista simple para selects en formularios
    public List<SedeDTO> listarActivas() {
        return sedeRepository.findByActivoTrue()
                .stream()
                .map(sedeMapper::toDTO)
                .toList();
    }

    // desactivar sede
    public SedeDTO desactivar(Long id) {
        Sede sede = sedeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Sede no encontrada"));
        sede.setActivo(false);
        sedeRepository.save(sede);
        return sedeMapper.toDTO(sede);
    }

    public SedeDTO activar(Long id) {
        Sede sede = sedeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Sede no encontrada"));
        sede.setActivo(true);
        sedeRepository.save(sede);
        return sedeMapper.toDTO(sede);
    }
}
