package com.envios.api_envios.envios;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.envios.api_envios.productos.Productos;
import com.envios.api_envios.rutas.RutaSedeService;
import com.envios.api_envios.tarifa.TarifaAdicional;
import com.envios.api_envios.tarifa.TarifaAdicionalRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EnviosService {

    private final EnviosRepository enviosRepository;
    private final EnviosMapper enviosMapper;
    private final RutaSedeService rutaSedeService;
    private final TarifaAdicionalRepository tarifaRepository;
    private final CodigoEnvioGenerator codigoGenerator;

    public EnviosDTO guardar(EnviosDTO enviosDTO) {
        validarRuta(enviosDTO.sedeOrigenId(), enviosDTO.sedeDestinoId());

        Envios envio = enviosMapper.toEntity(enviosDTO);
        envio.setCodigoEnvio(codigoGenerator.generar());

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

        Double recargo = calcularRecargo(pesoTotal, volumenTotal);

        envio.getPago().setMonto(montoBase + recargo);
        envio.setPesoTotal(pesoTotal);
        envio.setVolumenTotal(volumenTotal);

        return enviosMapper.toDTO(enviosRepository.save(envio));
    }

    private Double calcularRecargo(Double pesoTotal, Double volumenTotal) {
        TarifaAdicional tarifa = tarifaRepository.findTopBy().orElse(null);
        if (tarifa == null) return 0.0;

        double recargo = 0.0;

        if (pesoTotal > tarifa.getLimitePeso()) {
            recargo += (pesoTotal - tarifa.getLimitePeso()) * tarifa.getRecargoPeso();
        }
        if (volumenTotal > tarifa.getLimiteVolumen()) {
            recargo += (volumenTotal - tarifa.getLimiteVolumen()) * tarifa.getRecargoVolumen();
        }

        return recargo;
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

    // ✅ Buscar por código de envío
    public EnviosDTO getEnvioByCodigo(String codigoEnvio) {
        Envios envio = enviosRepository.findByCodigoEnvio(codigoEnvio)
                .orElseThrow(() -> new IllegalArgumentException("Envío no encontrado con código: " + codigoEnvio));
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
            // ✅ Usa el query correcto (origen O destino)
            return enviosRepository.findBySedeOrigenOrSedeDestino(sedeId, pageable)
                    .map(enviosMapper::toDTO);
        }
        return enviosRepository.findAll(pageable)
                .map(enviosMapper::toDTO);
    }

    public EnviosDTO actualizarEnvio(Long id, EnviosDTO enviosDTO) {
        Envios envio = enviosRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Envio no encontrado"));
        enviosMapper.updateEntity(envio, enviosDTO);
        enviosRepository.save(envio);
        return enviosMapper.toDTO(envio);
    }
}