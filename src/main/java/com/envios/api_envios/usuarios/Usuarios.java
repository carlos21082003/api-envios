package com.envios.api_envios.usuarios;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "usuarios")
@AllArgsConstructor
@NoArgsConstructor
public class Usuarios {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String dni;
    private String telefono;
    private String direccion;
    private String distrito;
    private String provinciaCliente;
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private Roles rol;

    private Boolean recojoDomicilio;
    private Boolean entregaDomicilio;
}
