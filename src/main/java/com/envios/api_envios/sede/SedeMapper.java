package com.envios.api_envios.sede;

import org.springframework.stereotype.Component;

@Component
public class SedeMapper {
    public SedeDTO toDTO(Sede sede) {
        return new SedeDTO(
                sede.getId(),
                sede.getNombre(),
                sede.getProvincia(),
                sede.getDireccion(),
                sede.getTelefono(),
                sede.getEsPrincipal(),
                sede.getTieneRecojo(),
                sede.getTieneDelivery(),
                sede.getActivo()
        );
    }

    public Sede toEntity(SedeDTO dto) {
        Sede sede = new Sede();
        sede.setNombre(dto.nombre());
        sede.setProvincia(dto.provincia());
        sede.setDireccion(dto.direccion());
        sede.setTelefono(dto.telefono());
        sede.setEsPrincipal(dto.esPrincipal() != null ? dto.esPrincipal() : false);
        sede.setTieneRecojo(dto.tieneRecojo() != null ? dto.tieneRecojo() : false);
        sede.setTieneDelivery(dto.tieneDelivery() != null ? dto.tieneDelivery() : false);
        sede.setActivo(true);
        return sede;
    }

    public void updateEntity(Sede sede, SedeDTO dto) {
        sede.setNombre(dto.nombre());
        sede.setProvincia(dto.provincia());
        sede.setDireccion(dto.direccion());
        sede.setTelefono(dto.telefono());
        sede.setEsPrincipal(dto.esPrincipal());
        sede.setTieneRecojo(dto.tieneRecojo());
        sede.setTieneDelivery(dto.tieneDelivery());
        sede.setActivo(dto.activo());
    }
}
