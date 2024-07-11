package com.api.appTransitionBanks.controller;


import com.api.appTransitionBanks.dto.AuthRequestDTO;
import com.api.appTransitionBanks.entities.auth.UserDetailsImpl;
import com.api.appTransitionBanks.service.impl.AuthService;
import com.api.appTransitionBanks.service.impl.TokenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    private final AuthenticationManager authenticationManager;

    private final TokenService tokenService;



    @CrossOrigin
    @PostMapping(value = "/signin", consumes =  "application/json")
    public ResponseEntity auth(@RequestBody @Valid AuthRequestDTO authRequest){
        try {
            var usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(authRequest.numberAccount(), authRequest.password());
            var auth = this.authenticationManager.authenticate(usernamePasswordAuthenticationToken);
            SecurityContextHolder.getContext().setAuthentication(auth);
            var token = tokenService.generateJwtToken(auth);

            return ResponseEntity.ok(authService.authenticatedResponse(token, (UserDetailsImpl) auth.getPrincipal()));
        } catch (AuthenticationException e){
            throw new BadCredentialsException(e.getMessage() + " Usuário ou senha inválidos");
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().body((HttpStatus.BAD_REQUEST.value()));
        }

    }
}
