package com.example.demo.model.repositories;

import com.example.demo.model.entities.Evaluation;
import com.example.demo.model.entities.Thematique;
import com.example.demo.model.entities.Utilisateur;

import jakarta.transaction.Transactional;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface EvaluationRepository extends JpaRepository<Evaluation, Integer> {
    Evaluation findByBeneficiareAndThematique(Utilisateur utilisateur, Thematique thematique);
    List<Evaluation> findAll();
    @Transactional
    @Modifying
    @Query("DELETE FROM Evaluation e WHERE e.beneficiare.id = :beneficiareId")
    void deleteByBeneficiareId(Integer beneficiareId);
    @Transactional
    @Modifying
    @Query("DELETE FROM Evaluation e WHERE e.thematique.id = :thematiqueId")
    void deleteByThematiqueId(Integer thematiqueId);
}