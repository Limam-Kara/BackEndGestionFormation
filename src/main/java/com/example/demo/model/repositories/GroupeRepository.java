package com.example.demo.model.repositories;

import com.example.demo.model.entities.Groupe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface GroupeRepository extends JpaRepository<Groupe, Integer> {

    Optional<Groupe> findById(Integer id);

    @Query("SELECT SUM(CAST(g.thematique.populationCible AS Integer)) FROM Groupe g WHERE g.thematique.id = :thematiqueId")
    Integer getTotalPopulationByThematiqueId(@Param("thematiqueId") Integer thematiqueId);
    List<Groupe> findByThematiqueId(Integer thematiqueId);

}