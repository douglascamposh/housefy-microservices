package com.dech.housefy.config;

import com.dech.housefy.security.JwtAuthenticationFilter;
import com.dech.housefy.security.AccessFilter;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AccessFilter accessFilter;
    private final AuthenticationProvider authenticationProvider;

    private static final String[] WHITE_LIST_URLS = {
        //Swagger ui
        "/api-docs",
        "/swagger-ui/**",
        "/swagger-resources",
        "/swagger-resources/**",
        // public
        "/",
        "/api/v1/auth/*",
        "/api/v1/properties",
        "/api/v1/properties/*",
        "/api/v1/properties/**",
        "/api/v1/sales",
        "/api/v1/customers",
        "/api/v1/config",
        "/api/v1/config/*",
        "/api/v1/saleman",
        "/api/v1/saleman/*",
        "/api/v1/roles",
        "/api/v1/roles/*"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request -> request.requestMatchers(WHITE_LIST_URLS)
                        .permitAll()
                        .anyRequest().authenticated()
                )
                .sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(accessFilter, JwtAuthenticationFilter.class);
//        http.cors();

        return http.build();
    }
}
