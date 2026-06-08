package com.envios.api_envios.auditoria;

import com.envios.api_envios.jwt.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
@Order(1)
public class AuditoriaFilter extends OncePerRequestFilter {
    private final AuditoriaRepository auditoriaRepository;
    private final JwtService jwtService;

    private static final List<String> METODOS_AUDITADOS =
            List.of("POST", "PUT", "PATCH", "DELETE");

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {

        // ignorar GET y rutas de sistema
        if (!METODOS_AUDITADOS.contains(request.getMethod()) ||
                request.getRequestURI().contains("/actuator") ||
                request.getRequestURI().equals("/health")) {
            chain.doFilter(request, response);
            return;
        }

        long inicio = System.currentTimeMillis();
        ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(request, 1024);
        ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper(response);

        String dniUsuario = null;
        String rolUsuario = null;

        try {
            String authHeader = request.getHeader("Authorization");
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7);
                dniUsuario = jwtService.extractUsername(token);
                rolUsuario = jwtService.extractRol(token);
            }
        } catch (Exception ignored) {}

        chain.doFilter(wrappedRequest, wrappedResponse);

        long duracion = System.currentTimeMillis() - inicio;

        try {
            Auditoria log = new Auditoria();
            log.setMetodo(request.getMethod());
            log.setEndpoint(request.getRequestURI());
            log.setStatusCode(wrappedResponse.getStatus());
            log.setDniUsuario(dniUsuario);
            log.setRolUsuario(rolUsuario);
            log.setIpOrigen(obtenerIp(request));
            log.setDuracionMs(duracion);
            log.setFecha(LocalDateTime.now());

            byte[] body = wrappedRequest.getContentAsByteArray();
            if (body.length > 0) {
                String bodyStr = new String(body, StandardCharsets.UTF_8);
                log.setRequestBody(bodyStr.length() > 500
                        ? bodyStr.substring(0, 500) + "..."
                        : bodyStr);
            }

            if (wrappedResponse.getStatus() >= 400) {
                byte[] responseBody = wrappedResponse.getContentAsByteArray();
                if (responseBody.length > 0) {
                    String responseStr = new String(responseBody, StandardCharsets.UTF_8);
                    log.setMensajeError(responseStr.length() > 500
                            ? responseStr.substring(0, 500) + "..."
                            : responseStr);
                }
            }

            auditoriaRepository.save(log);

        } catch (Exception e) {
            System.err.println("Error al guardar auditoría: " + e.getMessage());
        }

        wrappedResponse.copyBodyToResponse();
    }

    private String obtenerIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        return (ip != null && !ip.isEmpty()) ? ip.split(",")[0] : request.getRemoteAddr();
    }
}
