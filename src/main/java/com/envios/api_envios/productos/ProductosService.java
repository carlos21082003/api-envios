package com.envios.api_envios.productos;

import com.envios.api_envios.envios.Envios;
import com.envios.api_envios.envios.EnviosRepository;
import com.envios.api_envios.tarifa.TarifaAdicional;
import com.envios.api_envios.tarifa.TarifaAdicionalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductosService {
    private final ProductosRepository productosRepository;
    private final ProductosMapper productosMapper;
    private final EnviosRepository enviosRepository;
    private final TarifaAdicionalRepository tarifaRepository;

    @Transactional(readOnly = true)
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

        if (producto.getEnvio() != null) {
            recalcularTotalesEnvio(producto.getEnvio().getId());
        }

        return productosMapper.toDTO(producto);
    }

    private void recalcularTotalesEnvio(Long envioId) {
        Envios envio = enviosRepository.findById(envioId).orElse(null);
        if (envio == null) return;

        List<Productos> productos = envio.getProductos();

        Double montoBase = productos.stream()
                .mapToDouble(Productos::getPrecioPorProducto).sum();
        Double pesoTotal = productos.stream()
                .mapToDouble(Productos::getPesoTotal).sum();
        Double volumenTotal = productos.stream()
                .mapToDouble(Productos::getVolumenTotal).sum();

        Double recargo = calcularRecargo(pesoTotal, volumenTotal);

        envio.getPago().setMonto(montoBase + recargo);
        envio.setPesoTotal(pesoTotal);
        envio.setVolumenTotal(volumenTotal);
        enviosRepository.save(envio);
    }

    private Double calcularRecargo(Double pesoTotal, Double volumenTotal) {
        TarifaAdicional tarifa = tarifaRepository.findTopBy().orElse(null);
        if (tarifa == null) return 0.0;

        double recargo = 0.0;
        if (pesoTotal > tarifa.getLimitePeso())
            recargo += (pesoTotal - tarifa.getLimitePeso()) * tarifa.getRecargoPeso();
        if (volumenTotal > tarifa.getLimiteVolumen())
            recargo += (volumenTotal - tarifa.getLimiteVolumen()) * tarifa.getRecargoVolumen();
        return recargo;
    }

    @Transactional(readOnly = true)
    public Page<ProductosDTO> listar(int pagina, int cantidad) {
        Pageable pageable = PageRequest.of(pagina, cantidad);
        return productosRepository.findAll(pageable)
                .map(productosMapper::toDTO);
    }
}
