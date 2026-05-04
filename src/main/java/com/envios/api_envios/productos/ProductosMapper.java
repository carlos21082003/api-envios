package com.envios.api_envios.productos;

import com.envios.api_envios.tipoProducto.TipoProducto;
import com.envios.api_envios.tipoProducto.TipoProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductosMapper {

    private final TipoProductoRepository tipoProductoRepository;

    public Productos toEntity(ProductosDTO dto) {
        Productos producto = new Productos();

        TipoProducto tipoProducto = tipoProductoRepository.findById(dto.tipoProductoId())
                .orElseThrow(() -> new IllegalArgumentException("TipoProducto no encontrado"));

        producto.setTipoProducto(tipoProducto);
        producto.setDescripcion(dto.descripcion());
        producto.setNumeroPaquetes(dto.numeroPaquetes());
        return producto;
    }

    public ProductosDTO toDTO(Productos producto) {
        return new ProductosDTO(
                producto.getId(),
                producto.getTipoProducto().getId(),       // manda el id del tipo
                producto.getTipoProducto().getNombre(),   // manda el nombre para mostrarlo
                producto.getDescripcion(),
                producto.getNumeroPaquetes(),
                producto.getPrecioPorProducto()           // calculado con @Transient
        );
    }

    public void updateEntity(Productos producto, ProductosDTO dto) {
        TipoProducto tipoProducto = tipoProductoRepository.findById(dto.tipoProductoId())
                .orElseThrow(() -> new IllegalArgumentException("TipoProducto no encontrado"));

        producto.setTipoProducto(tipoProducto);
        producto.setDescripcion(dto.descripcion());
        producto.setNumeroPaquetes(dto.numeroPaquetes());
    }
}
