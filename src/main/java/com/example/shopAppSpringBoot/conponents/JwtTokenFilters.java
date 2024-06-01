package com.example.shopAppSpringBoot.conponents;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.util.Pair;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtTokenFilters extends OncePerRequestFilter{
    @Value("${api.prefix}")
    String apiPrefix;
    private final UserDetailsService userDetailsService;
    private final JwtTokenUtil jwtTokenUtil;
    @Override
    protected void doFilterInternal(
            @NotNull HttpServletRequest request,
            @NotNull HttpServletResponse response,
            @NotNull FilterChain filterChain) throws ServletException, IOException
    {
        //enable bypass
        if (isBypassToken(request)){
            filterChain.doFilter(request, response);//enable bypass
            return;
        }
        final String authHeader = request.getHeader("Authorization");
        if (authHeader != null || authHeader.startsWith("Bearer ")){
            final String token = authHeader.substring(7);
            final String phoneNumber = jwtTokenUtil.extractPhoneNumber(token);
            System.out.println("day la phone numbber " + phoneNumber);
            if(phoneNumber != null && SecurityContextHolder.getContext().getAuthentication() == null){
               UserDetails userDetails = userDetailsService.loadUserByUsername(phoneNumber);
                if(jwtTokenUtil.vailidateToken(token, userDetails)){
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities()
                            );
                }
            }
        }
        filterChain.doFilter(request, response);//enable bypass
    }


    private boolean isBypassToken(@NotNull HttpServletRequest request){
        final List<Pair<String, String>> bypassTokens = Arrays.asList(
                Pair.of(apiPrefix+"/user/register","POST"),
                Pair.of(apiPrefix+"/user/login","POST"),
                Pair.of(apiPrefix+"/category","GET"),
                Pair.of(apiPrefix+"/product","GET")
        );
        for (Pair<String, String> bypassToken : bypassTokens){
            if (request.getServletPath().contains(bypassToken.getFirst()) &&
                    request.getMethod().equals(bypassToken.getSecond())
            ){
                return true;

            }

        }
        return false;
    }
}
