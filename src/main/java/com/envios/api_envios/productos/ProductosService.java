package com.envios.api_envios.productos;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class ProductosService {
    private final ProductosRepository productosRepository;

    //metodo para guardar producto
    public Productos guardar(ProductosDTO productoDTO) {
        Productos productos = new Productos();

        productos.setTipoProducto(productoDTO.tipoProducto());
        productos.setDescripcion(productoDTO.descripcion());
        productos.setNumeroPaquetes(productoDTO.numeroPaquetes());
        return productosRepository.save(productos);
    }

    //metodo para listar por id
    public ProductosDTO getProductoById(Long id) {
        Productos producto = productosRepository.findById(id)
                .orElseThrow(() -> new  IllegalArgumentException("Producto no encontrado"));

        ProductosDTO response = new ProductosDTO(
                producto.getId(),
                producto.getTipoProducto(),
                producto.getDescripcion(),
                producto.getNumeroPaquetes()
        );
        return response;
    }
}
