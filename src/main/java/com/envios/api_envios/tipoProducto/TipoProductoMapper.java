package com.envios.api_envios.tipoProducto;

import org.springframework.stereotype.Component;

@Component

public class TipoProductoMapper {

    public TipoProducto toEntity(TipoProductoDTO dto) {
        TipoProducto tipo = new TipoProducto();
        tipo.setNombre(dto.nombre());
        tipo.setPrecioBase(dto.precioBase());
        tipo.setDescripcion(dto.descripcion());
        return tipo;
    }

    public TipoProductoDTO toDTO(TipoProducto tipo) {
        return new TipoProductoDTO(
                tipo.getId(),
                tipo.getNombre(),
                tipo.getPrecioBase(),
                tipo.getDescripcion()
        );
    }

    public void updateEntity(TipoProducto tipo, TipoProductoDTO dto) {
        tipo.setNombre(dto.nombre());
        tipo.setPrecioBase(dto.precioBase());
        tipo.setDescripcion(dto.descripcion());
    }
}
