package com.exp2.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Clase de configuración de seguridad para la aplicación.
 * Define la cadena de filtros de seguridad de Spring Security.
 * Deshabilita CSRF y permite todas las solicitudes sin autenticación,
 * incluyendo el acceso libre a la documentación Swagger.
 */
@Configuration
public class SecurityConfig {

    /**
     * Configura la cadena de filtros de seguridad de Spring Security.
     * Deshabilita CSRF y permite todas las solicitudes, incluyendo las rutas de
     * Swagger UI.
     * 
     * @param http Objeto HttpSecurity para configurar la seguridad HTTP.
     * @return SecurityFilterChain configurada.
     * @throws Exception Si ocurre un error en la configuración.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                        .anyRequest().permitAll());
        return http.build();
    }

}