package com.example.shopAppSpringBoot.configurations;

import com.example.shopAppSpringBoot.models.User;
import com.example.shopAppSpringBoot.reposotories.UserRepository;
import com.example.shopAppSpringBoot.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserRepository userRepository;


    String hoVaTen = "Tran Huu Dung";


    //set up username
    @Bean
    public UserDetailsService userDetailsService(){
        return phoneNumber ->  userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(
                    () ->new UsernameNotFoundException("can't found phone number " + phoneNumber)
                );

    }

    //setup and encoder password
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider (){

        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();

        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());

        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager (
            AuthenticationConfiguration config
    ) throws Exception {
        return config.getAuthenticationManager();
    }


}
