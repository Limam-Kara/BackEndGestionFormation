package com.example.demo.model.repositories;

import com.example.demo.model.entities.Q_Reponse;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface Q_ReponseRepository extends JpaRepository<Q_Reponse, Integer> {
    List<Q_Reponse> findAllByEvaluationId(Integer evaluationId);

}