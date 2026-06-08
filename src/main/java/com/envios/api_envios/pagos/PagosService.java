package com.envios.api_envios.pagos;

import com.envios.api_envios.envios.Envios;
import com.envios.api_envios.envios.EnviosRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor

public class PagosService {
    private final PagosRepository pagosRepository;
    private final PagosMapper pagosMapper;
    private final EnviosRepository enviosRepository;

    public PagosDTO getPagoById(Long id) {
        Pagos pago = pagosRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No existe un registro de pago con el identificador proporcionado"));
        return pagosMapper.toDTO(pago);
    }

    public PagosDTO actualizarEstado(Long id, PagosDTO pagosDTO) {
        Pagos pago = pagosRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No se pudo localizar el pago solicitado"));

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

    @Transactional
    public PagosDTO pagarEnLinea(Long envioId) {
        Envios envio = enviosRepository.findById(envioId)
                .orElseThrow(() -> new IllegalArgumentException("Envío no encontrado"));

        Pagos pago = pagosRepository.findByIdWithLock(envio.getPago().getId())
                .orElseThrow(() -> new IllegalArgumentException("No fue posible obtener la información del pago"));

        if (pago.getEstadoPago() == EstadoPago.PAGADO ||
                pago.getEstadoPago() == EstadoPago.PAGADOENLINEA) {
            return pagosMapper.toDTO(pago);
        }

        pago.setEstadoPago(EstadoPago.PAGADOENLINEA);
        pago.setMetodoPago("TARJETA_EN_LINEA");
        pago.setFechaPago(LocalDateTime.now());

        pagosRepository.save(pago);
        return pagosMapper.toDTO(pago);
    }
}
