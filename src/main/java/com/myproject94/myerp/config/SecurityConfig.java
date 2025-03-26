package com.myproject94.myerp.config;

import com.myproject94.myerp.security.JWTAuthenticationFilter;
import com.myproject94.myerp.security.JWTAuthorizationFilter;
import com.myproject94.myerp.security.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final Environment env;
    private final JWTUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    private static final String[] PUBLIC_URLS = {
            "/swagger-ui.html",
            "/h2-console/**",
            "/v2/api-docs",
            "/v3/api-docs",
            "/v3/api-docs/**",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui/**",
            "/webjars/**"
    };

    //  Define o AuthenticationManager
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    //  Define o AuthenticationProvider (login via UserDetailsService + BCrypt)
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(bCryptPasswordEncoder());
        return provider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationManager authManager) throws Exception {
// Configuração condicional para perfil de teste
        if (Arrays.asList(env.getActiveProfiles()).contains("test")) {
            http.headers(headers -> headers
                    .frameOptions(frameOptions -> frameOptions.disable())
            );
        }

        http
                // Desabilita CSRF
                .csrf(AbstractHttpConfigurer::disable)

                // Configuração de Headers para todos os perfis
                .headers(headers -> headers
                        .frameOptions(frameOptions -> frameOptions.disable()) // Desabilita X-Frame-Options
                        .contentSecurityPolicy(csp -> csp
                                .policyDirectives("frame-ancestors 'self'") // Política de segurança para frames
                        )
                )

                // Configuração CORS
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))

                // Gerenciamento de sessão sem estado
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // Autorização de requisições
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(PUBLIC_URLS).permitAll() // Libera URLs públicas
                        .anyRequest().authenticated() // Demais rotas precisam de autenticação
                )

                // Provedor de autenticação
                .authenticationProvider(authenticationProvider())

                // Filtros de autenticação e autorização JWT
                .addFilterBefore(
                        new JWTAuthenticationFilter(authManager, jwtUtil),
                        UsernamePasswordAuthenticationFilter.class
                )
                .addFilterBefore(
                        new JWTAuthorizationFilter(authManager, jwtUtil, userDetailsService),
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }

    // Configuração CORS global
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration().applyPermitDefaultValues();
        configuration.setAllowedMethods(Arrays.asList("POST", "GET", "PUT", "DELETE", "OPTIONS"));
        configuration.addAllowedOriginPattern("*");
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    // Password Encoder (BCrypt)
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}