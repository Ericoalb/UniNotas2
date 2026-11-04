package br.com.edge.UniNotas.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // 1. Rotas públicas (login/autenticação e documentação Swagger)
                        .requestMatchers("/v1/auth/**").permitAll()
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()

                        // 2. Rotas de consulta (GET): permitidas para perfis ALUNO e ADMIN
                        .requestMatchers(HttpMethod.GET,
                                "/v1/colleges/**", "/v1/classes/**", "/v1/students/**",
                                "/v1/college/**", "/v1/class/**", "/v1/student/**")
                        .hasAnyAuthority("ALUNO", "ROLE_ALUNO", "ADMIN", "ROLE_ADMIN")

                        // 3. Exclusões (DELETE): exclusivas para perfil ADMIN
                        .requestMatchers(HttpMethod.DELETE, "/v1/**")
                        .hasAnyAuthority("ADMIN", "ROLE_ADMIN")

                        // 4. Criação e atualização de Faculdades e Turmas (POST e PUT): apenas ADMIN
                        .requestMatchers(HttpMethod.POST,
                                "/v1/colleges/**", "/v1/classes/**",
                                "/v1/college/**", "/v1/class/**")
                        .hasAnyAuthority("ADMIN", "ROLE_ADMIN")
                        .requestMatchers(HttpMethod.PUT,
                                "/v1/colleges/**", "/v1/classes/**",
                                "/v1/college/**", "/v1/class/**")
                        .hasAnyAuthority("ADMIN", "ROLE_ADMIN")

                        // 5. Criação e atualização de Estudantes (POST e PUT): ALUNO e ADMIN
                        .requestMatchers(HttpMethod.POST,
                                "/v1/students/**", "/v1/student/**")
                        .hasAnyAuthority("ALUNO", "ROLE_ALUNO", "ADMIN", "ROLE_ADMIN")
                        .requestMatchers(HttpMethod.PUT,
                                "/v1/students/**", "/v1/student/**")
                        .hasAnyAuthority("ALUNO", "ROLE_ALUNO", "ADMIN", "ROLE_ADMIN")

                        // 6. Qualquer outra rota exige autenticação JWT
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
