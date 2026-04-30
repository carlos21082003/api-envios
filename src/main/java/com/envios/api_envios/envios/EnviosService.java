package com.envios.api_envios.envios;

import com.envios.api_envios.pagos.Pagos;
import com.envios.api_envios.pagos.PagosDTO;
import com.envios.api_envios.productos.Productos;
import com.envios.api_envios.productos.ProductosDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class EnviosService {
    private final EnviosRepository enviosRepository;

    //metodo para guardar
    public Envios guardar(EnviosDTO enviosDTO) {
        // Mapear pago
        Pagos pago = new Pagos();
        pago.setMonto(enviosDTO.pago().monto());
        pago.setMetodoPago(enviosDTO.pago().metodoPago());
        pago.setFechaPago(enviosDTO.pago().fechaPago());
        pago.setEstadoPago(enviosDTO.pago().estadoPago());

        // Mapear producto
        Productos producto = new Productos();
        producto.setTipoProducto(enviosDTO.producto().tipoProducto());
        producto.setDescripcion(enviosDTO.producto().descripcion());
        producto.setNumeroPaquetes(enviosDTO.producto().numeroPaquetes());

        // Mapear envio
        Envios envio = new Envios();
        envio.setPago(pago);
        envio.setProducto(producto);
        envio.setHoraSalida(enviosDTO.horaSalida());
        envio.setHoraLlegada(enviosDTO.horaLlegada());
        envio.setFechaEnvio(enviosDTO.fechaEnvio());
        envio.setNombreDestinatario(enviosDTO.nombreDestinatario());
        envio.setDniDestinatario(enviosDTO.dniDestinatario());
        envio.setNombreRemitente(enviosDTO.nombreRemitente());
        envio.setDniRemitente(enviosDTO.dniRemitente());
        envio.setEstadoEnvio(enviosDTO.estadoEnvio());

        return enviosRepository.save(envio); 
    }

    // metodo para buscar por DNI del remitente
    public EnviosDTO getEnvioByDniRemitente(String dniRemitente) {
        Envios envio = enviosRepository.findByDniRemitente(dniRemitente)
                .orElseThrow(() -> new IllegalArgumentException("Envio no encontrado"));

        PagosDTO pagoDTO = new PagosDTO(
                envio.getPago().getId(),
                envio.getPago().getMonto(),
                envio.getPago().getMetodoPago(),
                envio.getPago().getFechaPago(),
                envio.getPago().getEstadoPago()
        );

        ProductosDTO productoDTO = new ProductosDTO(
                envio.getProducto().getId(),
                envio.getProducto().getTipoProducto(),
                envio.getProducto().getDescripcion(),
                envio.getProducto().getNumeroPaquetes()
        );

        EnviosDTO response = new EnviosDTO(
                envio.getId(),
                envio.getHoraSalida(),
                envio.getHoraLlegada(),
                envio.getFechaEnvio(),
                envio.getNombreDestinatario(),
                envio.getDniDestinatario(),
                envio.getNombreRemitente(),
                envio.getDniRemitente(),
                envio.getEstadoEnvio(),
                pagoDTO,
                productoDTO
        );
        return response;
    }

    public EstadoEnvio actualizarEstado(Long id, EstadoEnvio nuevoEstado) {
        Envios envio = enviosRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Envio no encontrado"));

        envio.setEstadoEnvio(nuevoEstado);
        enviosRepository.save(envio);

        return envio.getEstadoEnvio();
    }
}
