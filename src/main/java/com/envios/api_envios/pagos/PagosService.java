package com.envios.api_envios.pagos;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class PagosService {
    private final PagosRepository pagosRepository;
    private final PagosMapper pagosMapper;

    public Pagos guardar(PagosDTO pagosDTO) {
        Pagos pago = pagosMapper.toEntity(pagosDTO);
        return pagosRepository.save(pago);
    }

    public PagosDTO getPagoById(Long id) {
        Pagos pago = pagosRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pago no encontrado"));
        return pagosMapper.toDTO(pago);
    }

    public PagosDTO actualizarEstado(Long id, PagosDTO pagosDTO) {
        Pagos pago = pagosRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pago no encontrado"));
        pagosMapper.updateEntity(pago, pagosDTO); 
        pagosRepository.save(pago);
        return pagosMapper.toDTO(pago);
    }
}
