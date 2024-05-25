package com.example.demo.model.repositories;

import com.example.demo.model.entities.Absence;

import jakarta.transaction.Transactional;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface AbsenceRepository extends JpaRepository<Absence, Integer> {
	@Transactional
	@Modifying
	@Query("DELETE FROM Absence a WHERE a.utilisateur.id = :idUtilisateur")
	void deleteByUtilisateurId(Integer idUtilisateur);

	@Transactional
	@Modifying
	@Query("DELETE FROM Absence a WHERE a.thematique.id = :thematiqueId")
	void deleteByThematiqueId(Integer thematiqueId);
	
    Optional<Absence> findByDateAbsenceAndUtilisateur_Id(LocalDate dateAbsence, Integer utilisateurId);

}