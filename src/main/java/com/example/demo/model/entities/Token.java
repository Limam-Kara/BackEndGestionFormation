package com.example.demo.model.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "token")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "token")
    private String token;

    @Column(name = "is_logged_out")
    private boolean loggedOut;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private Utilisateur utilisateur;

}