package com.envios.api_envios.productos;

import org.springframework.stereotype.Component;

@Component
public class ProductosMapper {
    public Productos toEntity(ProductosDTO dto) {
        Productos producto = new Productos();
        producto.setTipoProducto(dto.tipoProducto());
        producto.setDescripcion(dto.descripcion());
        producto.setNumeroPaquetes(dto.numeroPaquetes());
        return producto;
    }

    public ProductosDTO toDTO(Productos producto) {
        return new ProductosDTO(
                producto.getId(),
                producto.getTipoProducto(),
                producto.getDescripcion(),
                producto.getNumeroPaquetes()
        );
    }

    public void updateEntity(Productos producto, ProductosDTO dto) {
        producto.setTipoProducto(dto.tipoProducto());
        producto.setDescripcion(dto.descripcion());
        producto.setNumeroPaquetes(dto.numeroPaquetes());
    }
}
