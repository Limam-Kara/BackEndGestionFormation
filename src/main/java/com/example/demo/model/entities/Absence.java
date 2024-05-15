package com.example.demo.model.entities;

import lombok.Data;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "absence")
@Data // Lombok annotation to generate getters/setters, toString, equals, hashCode, etc.
public class Absence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "etat_absence")
    private String etatAbsence;

    
    @Column(name = "date_absence")
    private LocalDate dateAbsence;

    @ManyToOne
    @JoinColumn(name = "id_utilisateur", referencedColumnName = "id")
    private Utilisateur utilisateur;

    @ManyToOne
    @JoinColumn(name = "id_thematique", referencedColumnName = "id")
    private Thematique thematique;
    
    // Constructors, getters, setters, toString, equals, and hashCode will be automatically generated by Lombok
}
