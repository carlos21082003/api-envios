package com.envios.api_envios.rutas;

import com.envios.api_envios.sede.Sede;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "rutas_sedes")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RutaSede {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sede_origen_id")
    private Sede sedeOrigen;

    @ManyToOne
    @JoinColumn(name = "sede_destino_id")
    private Sede sedeDestino;

    private Boolean activo = true;
}
