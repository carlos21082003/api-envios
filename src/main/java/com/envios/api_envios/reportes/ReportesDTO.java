package com.envios.api_envios.reportes;

public record ReportesDTO(
        Long totalEnvios,
        Long enviosPorSalir,
        Long enviosEnTransito,
        Long enviosEntregados,

        Double totalRecaudado,
        Double totalEfectivo,
        Double totalTransferencia,
        Long pagosPendientes,
        Long pagosCompletados,
        Long pagadosAlRecoger,

        Double totalPaquetes
) {
}
