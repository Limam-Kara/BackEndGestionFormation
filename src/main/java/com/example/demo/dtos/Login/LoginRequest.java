package com.example.demo.dtos.Login;

import com.example.demo.model.entities.Utilisateur;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginRequest {
    private String username;
    private String password;
    public Utilisateur toUtilisateur() {
        return new Utilisateur(username, password); // Assuming Utilisateur constructor takes username and password
    }
}
