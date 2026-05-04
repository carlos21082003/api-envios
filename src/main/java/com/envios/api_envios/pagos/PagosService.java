package com.envios.api_envios.pagos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class PagosService {
    private final PagosRepository pagosRepository;
    private final PagosMapper pagosMapper;

    public PagosDTO getPagoById(Long id) {
        Pagos pago = pagosRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pago no encontrado"));
        return pagosMapper.toDTO(pago);
    }

    public PagosDTO actualizarEstado(Long id, PagosDTO pagosDTO) {
        Pagos pago = pagosRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pago no encontrado"));

        pago.setMetodoPago(pagosDTO.metodoPago());
        pago.setFechaPago(pagosDTO.fechaPago());
        pago.setEstadoPago(pagosDTO.estadoPago());

        pagosRepository.save(pago);
        return pagosMapper.toDTO(pago);
    }

    // PagosService.java
    public Page<PagosDTO> listarPagos(int pagina, int cantidad) {
        Pageable pageable = PageRequest.of(pagina, cantidad);
        return pagosRepository.findAll(pageable)
                .map(pagosMapper::toDTO);
    }
}
