package com.example.demo.model.repositories;

import com.example.demo.model.entities.Formation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FormationRepository extends JpaRepository<Formation, Integer> {
    List<Formation> findByUtilisateurId(Integer utilisateurId);
    Optional<Formation> findBySpecialiteAndNiveauEtudeAndDiplomeAndUtilisateur_Id(
            String specialite, String niveauEtude, String diplome, Integer utilisateurId);

}