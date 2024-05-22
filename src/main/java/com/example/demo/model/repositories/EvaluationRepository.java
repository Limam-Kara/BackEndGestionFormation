package com.example.demo.model.repositories;

import com.example.demo.model.entities.Evaluation;
import com.example.demo.model.entities.Thematique;
import com.example.demo.model.entities.Utilisateur;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EvaluationRepository extends JpaRepository<Evaluation, Integer> {
    Evaluation findByBeneficiareAndThematique(Utilisateur utilisateur, Thematique thematique);
    List<Evaluation> findAll();

}