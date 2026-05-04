package com.envios.api_envios.envios;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class EnviosService {
    private final EnviosRepository enviosRepository;
    private final EnviosMapper enviosMapper;

    public EnviosDTO guardar(EnviosDTO enviosDTO) {
        Envios envio = enviosMapper.toEntity(enviosDTO);

        Double montoCalculado = envio.getProducto().getPrecioPorProducto();
        envio.getPago().setMonto(montoCalculado);

        Envios guardado = enviosRepository.save(envio);
        return enviosMapper.toDTO(guardado);
    }

    public EnviosDTO getEnvioById(Long id) {
        Envios envio = enviosRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Envio no encontrado"));
        return enviosMapper.toDTO(envio);
    }

    public EnviosDTO getEnvioByDniRemitente(String dniRemitente) {
        Envios envio = enviosRepository.findByDniRemitente(dniRemitente)
                .orElseThrow(() -> new IllegalArgumentException("Envio no encontrado"));
        return enviosMapper.toDTO(envio); 
    }

    public Page<EnviosDTO> listarEnvios(int pagina, int cantidad) {
        Pageable pageable = PageRequest.of(pagina, cantidad);
        Page<Envios> envios = enviosRepository.findAll(pageable);
        return envios.map(envio -> enviosMapper.toDTO(envio));
    }

    public EnviosDTO actualizarEnvio(Long id, EnviosDTO enviosDTO) {
        Envios envio = enviosRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Envio no encontrado"));

        envio.setNombreRemitente(enviosDTO.nombreRemitente());
        envio.setDniRemitente(enviosDTO.dniRemitente());
        envio.setNombreDestinatario(enviosDTO.nombreDestinatario());
        envio.setDniDestinatario(enviosDTO.dniDestinatario());
        envio.setHoraSalida(enviosDTO.horaSalida());
        envio.setHoraLlegada(enviosDTO.horaLlegada());
        envio.setFechaEnvio(enviosDTO.fechaEnvio());
        envio.setProvincia(enviosDTO.provincia());
        envio.setEstadoEnvio(enviosDTO.estadoEnvio());

        enviosRepository.save(envio);
        return enviosMapper.toDTO(envio);
    }
}
