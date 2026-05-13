package com.envios.api_envios.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity

public class SecurityConfig {
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception{
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(Customizer.withDefaults())
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(auth -> auth
                        // ========== HEALTH CHECK ==========
                        .requestMatchers("/", "/health").permitAll()
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        //productos
                        .requestMatchers(HttpMethod.POST, "/api/v1/productos/guardar").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/productos/{id}").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/api/v1/productos/{id}").permitAll()
                        .requestMatchers(HttpMethod.GET,  "/api/v1/productos").permitAll()

                        //tipo producto
                        .requestMatchers(HttpMethod.GET,    "/api/v1/tipo-productos").permitAll()
                        .requestMatchers(HttpMethod.GET,    "/api/v1/tipo-productos/{id}").permitAll()
                        .requestMatchers(HttpMethod.POST,   "/api/v1/tipo-productos/guardar").permitAll()
                        .requestMatchers(HttpMethod.PUT,    "/api/v1/tipo-productos/{id}").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/tipo-productos/{id}").permitAll()

                        //pagos
                        .requestMatchers(HttpMethod.GET, "/api/v1/pagos/{id}").permitAll()
                        .requestMatchers(HttpMethod.PUT,  "/api/v1/pagos/{id}").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/pagos").permitAll()

                        //envios
                        .requestMatchers(HttpMethod.POST, "/api/v1/envios/guardarEnvio").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/envios/rastrear/{dniRemitente}").permitAll()
                        .requestMatchers(HttpMethod.PUT,  "/api/v1/envios/{id}").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/envios/{id}").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/envios").permitAll()

                        // sedes
                        .requestMatchers(HttpMethod.POST,   "/api/v1/sedes").permitAll()
                        .requestMatchers(HttpMethod.GET,    "/api/v1/sedes").permitAll()
                        .requestMatchers(HttpMethod.GET,    "/api/v1/sedes/todas").permitAll()
                        .requestMatchers(HttpMethod.GET,    "/api/v1/sedes/activas").permitAll()
                        .requestMatchers(HttpMethod.GET,    "/api/v1/sedes/{id}").permitAll()
                        .requestMatchers(HttpMethod.PUT,    "/api/v1/sedes/{id}").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/sedes/{id}").permitAll()
                        .requestMatchers(HttpMethod.PATCH, "/api/v1/sedes/{id}/activar").permitAll()

                        //ruta sedes
                        .requestMatchers(HttpMethod.POST,  "/api/v1/rutas-sedes").permitAll()
                        .requestMatchers(HttpMethod.GET,   "/api/v1/rutas-sedes").permitAll()
                        .requestMatchers(HttpMethod.GET,   "/api/v1/rutas-sedes/origen/{sedeOrigenId}").permitAll()
                        .requestMatchers(HttpMethod.PATCH, "/api/v1/rutas-sedes/{id}/estado").permitAll()
                        .requestMatchers(HttpMethod.PATCH, "/api/v1/sedes/{id}/activar").permitAll()

                        // solicitudes
                        // clientes pueden solicitar sin login
                        .requestMatchers(HttpMethod.POST, "/api/v1/solicitudes/recojo").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/solicitudes/delivery").permitAll()
                        .requestMatchers(HttpMethod.GET,  "/api/v1/solicitudes/cliente/{dni}").permitAll()
                        // empleados y admins gestionan las solicitudes
                        .requestMatchers(HttpMethod.GET,   "/api/v1/solicitudes/sede/{sedeId}").permitAll()
                        .requestMatchers(HttpMethod.GET,   "/api/v1/solicitudes/sede/{sedeId}/estado/{estado}").permitAll()
                        .requestMatchers(HttpMethod.PATCH, "/api/v1/solicitudes/{id}/aceptar").permitAll()
                        .requestMatchers(HttpMethod.PATCH, "/api/v1/solicitudes/{id}/rechazar").permitAll()
                        .requestMatchers(HttpMethod.PATCH, "/api/v1/solicitudes/{id}/completer").permitAll()

                        //Usuarios
                        .requestMatchers(HttpMethod.POST,   "/api/v1/usuarios/login").permitAll()
                        .requestMatchers(HttpMethod.POST,   "/api/v1/usuarios").permitAll()

                        .requestMatchers(HttpMethod.GET,   "/api/v1/usuarios").permitAll()
                        .requestMatchers(HttpMethod.GET,   "/api/v1/usuarios/{id}").permitAll()
                        .requestMatchers(HttpMethod.PUT,   "/api/v1/usuarios/{id}").permitAll()
                        .requestMatchers(HttpMethod.PATCH,   "/api/v1/usuarios/{id}/password").permitAll()

                        //reportes
                        .requestMatchers(HttpMethod.GET, "/api/v1/reportes").permitAll()

                        .anyRequest().authenticated()
                );
                //.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
