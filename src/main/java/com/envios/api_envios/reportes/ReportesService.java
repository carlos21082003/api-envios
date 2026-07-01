package com.envios.api_envios.reportes;

import com.envios.api_envios.envios.EnviosRepository;
import com.envios.api_envios.envios.EstadoEnvio;
import com.envios.api_envios.pagos.EstadoPago;
import com.envios.api_envios.pagos.MetodoPago;
import com.envios.api_envios.pagos.PagosRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReportesService {
    private final EnviosRepository enviosRepository;
    private final PagosRepository pagosRepository;

    public ReportesDTO getReporte() {
        return new ReportesDTO(
                // envios
                enviosRepository.count(),
                enviosRepository.countByEstadoEnvio(EstadoEnvio.PORSALIR),
                enviosRepository.countByEstadoEnvio(EstadoEnvio.ENTRANSITO),
                enviosRepository.countByEstadoEnvio(EstadoEnvio.ENTREGADO),

                // pagos
                pagosRepository.sumMonto(),
                pagosRepository.sumMontoByMetodoPago(MetodoPago.EFECTIVO),
                pagosRepository.sumMontoByMetodoPago(MetodoPago.TRANSFERENCIA),
                pagosRepository.countByEstadoPago(EstadoPago.PENDIENTE),
                pagosRepository.countByEstadoPago(EstadoPago.PAGADO),
                pagosRepository.countByEstadoPago(EstadoPago.PAGADOALRECOGER),
                pagosRepository.sumTotalPaquetes(),

                // nuevos
                enviosRepository.countByProvincia(),
                pagosRepository.countByEstadoPago(EstadoPago.PAGADO),
                pagosRepository.countByEstadoPago(EstadoPago.PENDIENTE),
                pagosRepository.countByEstadoPago(EstadoPago.PAGADOALRECOGER),

                // pago en línea (faltaba)
                pagosRepository.sumMontoByMetodoPago(MetodoPago.TARJETA_EN_LINEA),
                pagosRepository.countByEstadoPago(EstadoPago.PAGADOENLINEA)
        );
    }
}
