package com.envios.api_envios.productos;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class ProductosService {
    private final ProductosRepository productosRepository;
    private final ProductosMapper productosMapper;

    public ProductosDTO getProductoById(Long id) {
        Productos producto = productosRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));

        return productosMapper.toDTO(producto);
    }

    public ProductosDTO actualizar(Long id, ProductosDTO productoDTO) {
        Productos producto = productosRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));

        productosMapper.updateEntity(producto, productoDTO);
        productosRepository.save(producto);

        return productosMapper.toDTO(producto);
    }
}
