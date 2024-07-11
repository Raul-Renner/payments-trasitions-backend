package com.api.appTransitionBanks.service.impl;

import com.api.appTransitionBanks.entities.auth.UserDetailsImpl;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${spring.security.jwt.token.secret}")
    private String secret;


    public String generateJwtToken(Authentication authentication) {
        try {
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            //var userDetails = SecurityContextHolder.getContext().getAuthentication();
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("auth-api")
                    .withSubject(userDetails.getNumberAccount())
                    .withExpiresAt(genExpirationDate())
                    .sign(algorithm);
        } catch (Exception e){
            throw new JWTVerificationException("JWT token generation failed");
        }

    }



    public String validateToken(String token) {
        try {

            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("auth-api")
                    .build()
                    .verify(token)
                    .getSubject();

        } catch (JWTVerificationException exception){
            throw new JWTVerificationException(exception.getMessage());
        }
    }

    private Instant genExpirationDate(){
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
