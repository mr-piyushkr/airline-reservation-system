package com.airline.reservation.config;

import com.airline.reservation.security.JwtAuthenticationFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final ObjectMapper objectMapper;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                // Public auth endpoints
                .requestMatchers("/api/auth/**").permitAll()
                // Public flight read/search
                .requestMatchers(HttpMethod.GET, "/api/flights/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/airports/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/aircraft/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/seats/**").permitAll()
                // Admin-only management
                .requestMatchers(HttpMethod.POST, "/api/flights/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/flights/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/flights/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/airports/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/airports/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/airports/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/aircraft/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/aircraft/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/aircraft/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/seats/**").hasRole("ADMIN")
                .requestMatchers("/api/users/**").hasRole("ADMIN")
                // Profile endpoints for authenticated users
                .requestMatchers("/api/profile/**").authenticated()
                // Booking, payment, passenger — authenticated
                .requestMatchers("/api/bookings/**").authenticated()
                .requestMatchers("/api/payments/**").authenticated()
                .requestMatchers("/api/passengers/**").authenticated()
                .anyRequest().authenticated()
            )
            .exceptionHandling(ex -> ex
                .authenticationEntryPoint((request, response, authException) -> {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                    Map<String, Object> body = new LinkedHashMap<>();
                    body.put("timestamp", LocalDateTime.now().toString());
                    body.put("status", 401);
                    body.put("error", "Unauthorized");
                    body.put("message", "Authentication required");
                    body.put("path", request.getRequestURI());
                    response.getWriter().write(objectMapper.writeValueAsString(body));
                })
                .accessDeniedHandler((request, response, accessDeniedException) -> {
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                    Map<String, Object> body = new LinkedHashMap<>();
                    body.put("timestamp", LocalDateTime.now().toString());
                    body.put("status", 403);
                    body.put("error", "Forbidden");
                    body.put("message", "Access denied");
                    body.put("path", request.getRequestURI());
                    response.getWriter().write(objectMapper.writeValueAsString(body));
                })
            )
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:3000", "http://localhost:5173"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
