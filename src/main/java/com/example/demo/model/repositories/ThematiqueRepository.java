package com.example.demo.model.repositories;

import com.example.demo.model.entities.Thematique;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ThematiqueRepository extends JpaRepository<Thematique, Integer> {
	@Query("SELECT g.thematique FROM Groupe g WHERE g.id = :thematique_id")
    Thematique findThematiqueByGroupeId(@Param("thematique_id") Integer thematique_id);
}