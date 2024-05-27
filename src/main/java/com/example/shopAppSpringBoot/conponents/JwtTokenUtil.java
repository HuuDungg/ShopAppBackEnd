package com.example.shopAppSpringBoot.conponents;

import com.example.shopAppSpringBoot.models.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.*;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class JwtTokenUtil {
    //time is valid
    @Value("${jwt.expiration}")
    private int expiration;

    @Value("${jwt.secretKey}")
    private String secretKey;

    public String generateToken(User user){
        //properties => claims
        Map<String, String> claims = new HashMap<>();
        claims.put("phoneNumber", user.getPhoneNumber());

        try {
            String token = Jwts.builder()
                    .setClaims(claims)
                    .setSubject(user.getPassword())
                    .setExpiration(new Date(System.currentTimeMillis() + 1000 + expiration))
                    .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                    .compact();
            return token;
        }catch (Exception e){
            //you can use logger instead sout
            System.out.println("can't create jwt token, because: " + e.getMessage());
            return null;
        }

    }

    //trans string secretkey to key encoder
    private Key getSignInKey(){
        byte[] bytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(bytes);
    }

    //method to extract all claims by token
    private Claims extractAllClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey()) //insert key to extract claims
                .build()
                .parseClaimsJwt(token)
                .getBody();
    }

    //extract one claim by extrac all and get one
    public  <T> T extractClaim(String token, Function<Claims, T> claimResolver){
       final Claims claims = this.extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    //check expriration
    public boolean isTokenExpired(String token){
        //get day expiration
        Date expirationDate = this.extractClaim(token, Claims:: getExpiration);
        //compare with to day
        return expirationDate.before(new Date());
    }

    public String extractPhoneNumber(String token){
        return this.extractClaim(token, Claims::getSubject);
    }

}
