package com.envios.api_envios.solicitud;

public enum EstadoSolicitud {
    PENDIENTE,   // recién solicitado
    ACEPTADA,    // empleado aceptó
    RECHAZADA,   // sede no puede atenderlo
    COMPLETADA   // ya se hizo el recojo o delivery
}
