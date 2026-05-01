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

    public Envios guardar(EnviosDTO enviosDTO) {
        Envios envio = enviosMapper.toEntity(enviosDTO);  
        return enviosRepository.save(envio);
    }

    public EnviosDTO getEnvioByDniRemitente(String dniRemitente) {
        Envios envio = enviosRepository.findByDniRemitente(dniRemitente)
                .orElseThrow(() -> new IllegalArgumentException("Envio no encontrado"));
        return enviosMapper.toDTO(envio); 
    }

    public EstadoEnvio actualizarEstado(Long id, EstadoEnvio nuevoEstado) {
        Envios envio = enviosRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Envio no encontrado"));
        envio.setEstadoEnvio(nuevoEstado);
        enviosRepository.save(envio);
        return envio.getEstadoEnvio();
    }

    public Page<EnviosDTO> listarEnvios(int pagina, int cantidad) {
        Pageable pageable = PageRequest.of(pagina, cantidad);
        Page<Envios> envios = enviosRepository.findAll(pageable);
        return envios.map(envio -> enviosMapper.toDTO(envio));
    }
}
