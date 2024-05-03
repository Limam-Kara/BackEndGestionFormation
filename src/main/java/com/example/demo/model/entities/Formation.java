package com.example.demo.model.entities;

import lombok.Data;
import jakarta.persistence.*;

@Entity
@Table(name = "formation")
@Data // Lombok annotation to generate getters, setters, toString, equals, hashCode, etc.
public class Formation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "specialite")
    private String specialite;

    @Column(name = "diplome")
    private String diplome;

    @Column(name = "utilisateur_id")
    private Long utilisateurId;

    @Column(name = "niveau_etude")
    private String niveauEtude;

    // Constructors, getters, setters, toString, equals, and hashCode will be automatically generated by Lombok
}

