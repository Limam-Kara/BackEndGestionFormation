package com.example.demo.controllers.authentication;

import com.example.demo.Service.TokenService;
import com.example.demo.Service.impls.UtilisateurServiceImpl;
import com.example.demo.dtos.Login.LoginRequest;
import com.example.demo.dtos.Login.LoginResponse;
import com.example.demo.model.entities.Utilisateur;
import com.example.demo.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {
    private final UtilisateurServiceImpl utilisateurService;

    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Autowired
    public LoginController(UtilisateurServiceImpl utilisateurService, TokenService tokenService, AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.utilisateurService = utilisateurService;
        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        UserDetails userDetails;
        try {
            userDetails = utilisateurService.loadUserByUsername(loginRequest.getUsername());
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        String jwt = jwtUtil.generateToken(userDetails.getUsername());
        Utilisateur utilisateur = utilisateurService.getUtilisateurByUsername(userDetails.getUsername());
        tokenService.revokeAllTokensByUser(utilisateur);
        tokenService.saveToken(jwt, utilisateur);
        LoginResponse response = new LoginResponse(jwt, utilisateur.getId(), utilisateur.getIdRole().getNom());
        return ResponseEntity.ok(response);
    }
}