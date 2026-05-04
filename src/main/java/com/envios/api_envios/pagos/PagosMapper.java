package com.envios.api_envios.pagos;

import org.springframework.stereotype.Component;

@Component
public class PagosMapper {

    public Pagos toEntity(PagosDTO dto) {
        Pagos pago = new Pagos();

        pago.setMetodoPago(dto.metodoPago());
        pago.setFechaPago(dto.fechaPago());
        pago.setEstadoPago(dto.estadoPago());
        return pago;
    }

    public PagosDTO toDTO(Pagos pago) {
        return new PagosDTO(
                pago.getId(),
                pago.getMonto(),
                pago.getMetodoPago(),
                pago.getFechaPago(),
                pago.getEstadoPago()
        );
    }

    public void updateEntity(Pagos pago, PagosDTO dto) {
        pago.setMetodoPago(dto.metodoPago());
        pago.setFechaPago(dto.fechaPago());
        pago.setEstadoPago(dto.estadoPago());
    }
}
