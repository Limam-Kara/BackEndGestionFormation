package com.example.demo.model.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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

    @Column(name = "niveau_etude")
    private String niveauEtude;

    @ManyToOne
    @JoinColumn(name = "utilisateur_id")
    @JsonBackReference
    private Utilisateur utilisateur;

    // Constructors, getters, setters, toString, equals, and hashCode will be automatically generated by Lombok
}

