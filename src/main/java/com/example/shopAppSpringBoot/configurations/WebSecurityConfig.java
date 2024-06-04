package com.example.shopAppSpringBoot.configurations;

import com.example.shopAppSpringBoot.conponents.JwtTokenFilters;
import com.example.shopAppSpringBoot.models.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

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
                            .requestMatchers(
                                    String.format("%s/user/register", apiPrefix),
                                    String.format("%s/user/login", apiPrefix)
                            )
                                    .permitAll()

                                    .requestMatchers(HttpMethod.GET,
                                            String.format("%s/categories**", apiPrefix)).hasAnyRole(Role.USER, Role.ADMIN)

                                    .requestMatchers(HttpMethod.POST,
                                            String.format("%s/categories/**", apiPrefix)).hasAnyRole(Role.ADMIN)

                                    .requestMatchers(HttpMethod.PUT,
                                            String.format("%s/categories/**", apiPrefix)).hasAnyRole(Role.ADMIN)

                                    .requestMatchers(HttpMethod.DELETE,
                                            String.format("%s/categories/**", apiPrefix)).hasAnyRole(Role.ADMIN)

                                    .requestMatchers(HttpMethod.GET,
                                            String.format("%s/products**", apiPrefix)).hasAnyRole(Role.USER, Role.ADMIN)

                                    .requestMatchers(HttpMethod.POST,
                                            String.format("%s/products/**", apiPrefix)).hasAnyRole(Role.ADMIN)

                                    .requestMatchers(HttpMethod.PUT,
                                            String.format("%s/products/**", apiPrefix)).hasAnyRole(Role.ADMIN)

                                    .requestMatchers(HttpMethod.DELETE,
                                            String.format("%s/products/**", apiPrefix)).hasAnyRole(Role.ADMIN)


                                    .requestMatchers(HttpMethod.POST,
                                            String.format("%s/orders/**", apiPrefix)).hasAnyRole(Role.USER)

                                    .requestMatchers(HttpMethod.GET,
                                            String.format("%s/orders/**", apiPrefix)).hasAnyRole(Role.USER, Role.ADMIN)

                                    .requestMatchers(HttpMethod.PUT,
                                            String.format("%s/orders/**", apiPrefix)).hasRole(Role.ADMIN)

                                    .requestMatchers(HttpMethod.DELETE,
                                            String.format("%s/orders/**", apiPrefix)).hasRole(Role.ADMIN)

                                    .requestMatchers(HttpMethod.POST,
                                            String.format("%s/order_details/**", apiPrefix)).hasAnyRole(Role.USER)

                                    .requestMatchers(HttpMethod.GET,
                                            String.format("%s/order_details/**", apiPrefix)).hasAnyRole(Role.USER, Role.ADMIN)

                                    .requestMatchers(HttpMethod.PUT,
                                            String.format("%s/order_details/**", apiPrefix)).hasRole(Role.ADMIN)

                                    .requestMatchers(HttpMethod.DELETE,
                                            String.format("%s/order_details/**", apiPrefix)).hasRole(Role.ADMIN)

                                    .anyRequest().permitAll();
                        }
                )
                .cors(new Customizer<CorsConfigurer<HttpSecurity>>() {
                    @Override
                    public void customize(CorsConfigurer<HttpSecurity> httpSecurityCorsConfigurer) {
                        CorsConfiguration configuration = new CorsConfiguration();
                        configuration.setAllowedOrigins(List.of("*"));
                        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
                        configuration.setAllowedHeaders(Arrays.asList("authorization", "content-type", "x-auth-token"));
                        configuration.setExposedHeaders(List.of("x-auth-token"));
                        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                        source.registerCorsConfiguration("/**", configuration);
                        httpSecurityCorsConfigurer.configurationSource(source);
                    }
                })
                .build();
    }
}
