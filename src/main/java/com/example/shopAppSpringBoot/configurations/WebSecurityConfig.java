package com.example.shopAppSpringBoot.configurations;

import com.example.shopAppSpringBoot.conponents.JwtTokenFilters;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity
public class WebSecurityConfig {
    private final JwtTokenFilters jwtTokenFilters;

    @Value("${api.prefix}")
    private String apiPrefix;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity)throws Exception{
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(jwtTokenFilters, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(
                        authorize ->{
                            authorize
                                    .requestMatchers(apiPrefix + "/user/**").permitAll()
                                    .requestMatchers(HttpMethod.PUT).hasRole("admin")
                                    .requestMatchers(HttpMethod.DELETE).hasRole("admin")

                                    .anyRequest().permitAll();
                        }
                )
                .build();
    }
}
