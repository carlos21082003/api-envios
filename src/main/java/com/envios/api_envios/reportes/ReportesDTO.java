package com.envios.api_envios.reportes;

import java.util.List;

public record ReportesDTO(
        // envios existentes
        Long totalEnvios,
        Long enviosPorSalir,
        Long enviosEnTransito,
        Long enviosEntregados,

        // pagos existentes
        Double totalRecaudado,
        Double totalEfectivo,
        Double totalTransferencia,
        Long pagosPendientes,
        Long pagosCompletados,
        Long pagosPagadoAlRecoger,
        Double totalPaquetes,

        // nuevos para gráficas
        List<ProvinciasConteoDTO> enviosPorProvincia,
        Long pagosPagados,
        Long pagosPendientesGrafica,
        Long pagosPagadoAlRecogerGrafica
) {
}
