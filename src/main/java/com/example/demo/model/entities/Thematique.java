package com.example.demo.model.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import jakarta.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "thematique")
@Data // Lombok annotation to generate getters, setters, toString, equals, hashCode, etc.
public class Thematique {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "cout_logistique")
    private Double coutLogistique;

    @Column(name = "cout_pedagogique")
    private Double coutPedagogique;

    @Column(name = "date_debut")
    private Date dateDebut;

    @Column(name = "date_fin")
    private Date dateFin;

    @Column(name = "domaine_formation")
    private String domaineFormation;

    @Column(name = "intitule")
    private String intitule;

    @Column(name = "nbr_formateur_extr")
    private Integer nbrFormateurExtr;

    @Column(name = "nbr_formateur_intr")
    private Integer nbrFormateurIntr;

    @Column(name = "nbr_groupe")
    private Integer nbrGroupe;

    @Column(name = "nbr_jrs_formation")
    private Integer nbrJoursFormation;

    @Column(name = "objectif")
    private String objectif;

    @Column(name = "popu_cible")
    private String populationCible;

    @Column(name = "prestataire")
    private String prestataire;
    @OneToMany(mappedBy = "thematique", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Groupe> groupes;
}

