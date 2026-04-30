package com.envios.api_envios.pagos;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class PagosService {
    private final PagosRepository pagosRepository;

    //metodo para guardar el pago
    public Pagos guardar(PagosDTO pagosDTO) {
        Pagos pago = new Pagos();

        pago.setMonto(pagosDTO.monto());
        pago.setMetodoPago(pagosDTO.metodoPago());
        pago.setFechaPago(pagosDTO.fechaPago());
        pago.setEstadoPago(pagosDTO.estadoPago());
        return pagosRepository.save(pago);
    }

    // metodo para listar por id
    public PagosDTO getPagoById(Long id) {
        Pagos pago = pagosRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pago no encontrado"));

        PagosDTO response = new PagosDTO(
                pago.getId(),
                pago.getMonto(),
                pago.getMetodoPago(),
                pago.getFechaPago(),
                pago.getEstadoPago()
        );
        return response;
    }

    // metodo para actualizar el estado del pago
    public PagosDTO actualizarEstado(Long id, PagosDTO pagosDTO) {
        Pagos pago = pagosRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pago no encontrado"));

        pago.setEstadoPago(pagosDTO.estadoPago());
        pagosRepository.save(pago);

        return new PagosDTO(
                pago.getId(),
                pago.getMonto(),
                pago.getMetodoPago(),
                pago.getFechaPago(),
                pago.getEstadoPago()
        );
    }
}
