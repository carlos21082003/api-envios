package com.envios.api_envios.envios;

import com.envios.api_envios.productos.Productos;
import com.envios.api_envios.rutas.RutaSedeService;
import com.envios.api_envios.tarifa.TarifaAdicional;
import com.envios.api_envios.tarifa.TarifaAdicionalRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor

public class EnviosService {
    private final EnviosRepository enviosRepository;
    private final EnviosMapper enviosMapper;
    private final RutaSedeService rutaSedeService;
    private final TarifaAdicionalRepository tarifaRepository;

    public EnviosDTO guardar(EnviosDTO enviosDTO) {
        validarRuta(enviosDTO.sedeOrigenId(), enviosDTO.sedeDestinoId());

        Envios envio = enviosMapper.toEntity(enviosDTO);
        envio.setCodigoEnvio(generarCodigoUnico());

        List<Productos> productos = envio.getProductos();

        Double montoBase = productos.stream()
                .mapToDouble(Productos::getPrecioPorProducto)
                .sum();

        Double pesoTotal = productos.stream()
                .mapToDouble(Productos::getPesoTotal)
                .sum();

        Double volumenTotal = productos.stream()
                .mapToDouble(Productos::getVolumenTotal)
                .sum();

        // Calcular recargos
        Double recargo = calcularRecargo(pesoTotal, volumenTotal);

        envio.getPago().setMonto(montoBase + recargo);
        envio.setPesoTotal(pesoTotal);
        envio.setVolumenTotal(volumenTotal);

        return enviosMapper.toDTO(enviosRepository.save(envio));
    }

    private Double calcularRecargo(Double pesoTotal, Double volumenTotal) {
        TarifaAdicional tarifa = tarifaRepository.findTopBy()
                .orElse(null);
        if (tarifa == null) return 0.0;

        double recargo = 0.0;

        if (pesoTotal > tarifa.getLimitePeso()) {
            double pesoExtra = pesoTotal - tarifa.getLimitePeso();
            recargo += pesoExtra * tarifa.getRecargoPeso();
        }

        if (volumenTotal > tarifa.getLimiteVolumen()) {
            double volumenExtra = volumenTotal - tarifa.getLimiteVolumen();
            recargo += volumenExtra * tarifa.getRecargoVolumen();
        }

        return recargo;
    }

    private String generarCodigoUnico() {
        String codigo;
        do {
            codigo = String.format("%06d", new Random().nextInt(1_000_000));
        } while (enviosRepository.existsByCodigoEnvio(codigo));
        return codigo;
    }

    private void validarRuta(Long origenId, Long destinoId) {
        if (origenId != null && destinoId != null
                && !rutaSedeService.existeRuta(origenId, destinoId)) {
            throw new IllegalArgumentException(
                    "No existe una ruta habilitada entre estas sedes");
        }
    }

    public EnviosDTO getEnvioById(Long id) {
        Envios envio = enviosRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Envio no encontrado"));
        return enviosMapper.toDTO(envio);
    }

    public List<EnviosDTO> getEnviosByDniRemitente(String dniRemitente) {
        List<Envios> envios = enviosRepository.findByDniRemitenteOrderByFechaEnvioDesc(dniRemitente);
        if (envios.isEmpty()) {
            throw new IllegalArgumentException("Envío no encontrado");
        }
        return envios.stream().map(enviosMapper::toDTO).toList();
    }

    public Page<EnviosDTO> listarEnvios(int pagina, int cantidad, Long sedeId) {
        Pageable pageable = PageRequest.of(pagina, cantidad);

        if (sedeId != null) {
            return enviosRepository.findBySedeOrigenOrSedeDestino(sedeId, pageable)
                    .map(enviosMapper::toDTO);
        }
        return enviosRepository.findAll(pageable)
                .map(enviosMapper::toDTO);
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
