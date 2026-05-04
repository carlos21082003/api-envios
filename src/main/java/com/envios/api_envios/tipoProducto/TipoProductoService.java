package com.envios.api_envios.tipoProducto;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TipoProductoService {
    private final TipoProductoRepository tipoProductoRepository;
    private final TipoProductoMapper tipoProductoMapper;

    public TipoProductoDTO getById(Long id) {
        TipoProducto tipo = tipoProductoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("TipoProducto no encontrado"));
        return tipoProductoMapper.toDTO(tipo);
    }

    public TipoProductoDTO guardar(TipoProductoDTO dto) {
        TipoProducto tipo = tipoProductoMapper.toEntity(dto);
        tipoProductoRepository.save(tipo);
        return tipoProductoMapper.toDTO(tipo);
    }

    public TipoProductoDTO actualizar(Long id, TipoProductoDTO dto) {
        TipoProducto tipo = tipoProductoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("TipoProducto no encontrado"));
        tipoProductoMapper.updateEntity(tipo, dto);
        tipoProductoRepository.save(tipo);
        return tipoProductoMapper.toDTO(tipo);
    }

    public Page<TipoProductoDTO> listar(int pagina, int cantidad) {
        Pageable pageable = PageRequest.of(pagina, cantidad);
        return tipoProductoRepository.findAll(pageable)
                .map(tipoProductoMapper::toDTO);
    }

    public void eliminar(Long id) {
        tipoProductoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("TipoProducto no encontrado"));
        tipoProductoRepository.deleteById(id);
    }
}
