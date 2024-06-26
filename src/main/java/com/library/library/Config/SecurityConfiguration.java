package com.library.library.Config;

import com.library.library.Filters.JwtAuthFilter;
import com.library.library.Authentication.ComposedDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfiguration {
        private final ComposedDetailsService composedDetailsService;
        private final JwtAuthFilter jwtAuthFilter;

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                return http.csrf(AbstractHttpConfigurer::disable)
                                .authorizeHttpRequests(
                                                req -> req

                                                                .requestMatchers("/v3/api-docs/**",
                                                                                "/swagger-ui/**", "/swagger-ui.html",
                                                                                "/v3/api-docs/**",
                                                                                "/swagger-ui.html",
                                                                                "/swagger-ui/**",
                                                                                "/swagger-resources/**",
                                                                                "/webjars/**")
                                                                .permitAll()
                                                                .requestMatchers("/public/**")
                                                                .permitAll()
                                                                .anyRequest()
                                                                .authenticated())
                                .userDetailsService(composedDetailsService)
                                .sessionManagement( // No need for sessions
                                                session -> session
                                                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                                .exceptionHandling(
                                                e -> e.accessDeniedHandler(
                                                                (request, response, accessDeniedException) -> response
                                                                                .setStatus(401))
                                                                .authenticationEntryPoint(new HttpStatusEntryPoint(
                                                                                HttpStatus.UNAUTHORIZED)))
                                .build();
        }
}
