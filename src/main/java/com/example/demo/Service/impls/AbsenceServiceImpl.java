package com.example.demo.Service.impls;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.entities.Absence;

import com.example.demo.model.repositories.AbsenceRepository;

@Service
public class AbsenceServiceImpl {

	 @Autowired
	    private AbsenceRepository absenceRepository;

//	    public List<Absence> saveAbsences(List<Absence> absences) {
//	        return absenceRepository.saveAll(absences);
//	    }
		/*
		 * public List<Absence> saveAbsences(List<Absence> absences) { List<Absence>
		 * savedAbsences = new ArrayList<>();
		 * 
		 * for (Absence absence : absences) { // Check if the absence already exists in
		 * the database Optional<Absence> existingAbsenceOptional =
		 * absenceRepository.findById(absence.getId()); if
		 * (existingAbsenceOptional.isPresent()) { // If the absence exists, update it
		 * Absence existingAbsence = existingAbsenceOptional.get();
		 * existingAbsence.setEtatAbsence(absence.getEtatAbsence());
		 * existingAbsence.setDateAbsence(absence.getDateAbsence());
		 * existingAbsence.setUtilisateur(absence.getUtilisateur());
		 * existingAbsence.setThematique(absence.getThematique());
		 * existingAbsence.setId_group(absence.getId_group());
		 * 
		 * savedAbsences.add(absenceRepository.save(existingAbsence)); } else { // If
		 * the absence doesn't exist, add it as a new absence
		 * savedAbsences.add(absenceRepository.save(absence)); } }
		 * 
		 * return savedAbsences; }
		 */
	 public List<Absence> saveAbsences(List<Absence> absences) {
	        List<Absence> savedAbsences = new ArrayList<>();

	        for (Absence absence : absences) {
	            // Check if an absence with the same date and user already exists
	            Optional<Absence> existingAbsenceWithSameDate = absenceRepository.findByDateAbsenceAndUtilisateur_Id(absence.getDateAbsence(), absence.getUtilisateur().getId());

	            if (existingAbsenceWithSameDate.isPresent()) {
	                // If an absence with the same date and user exists, update it
	                Absence existingAbsence = existingAbsenceWithSameDate.get();
	                existingAbsence.setEtatAbsence(absence.isEtatAbsence());
	                existingAbsence.setDateAbsence(absence.getDateAbsence());
	                existingAbsence.setUtilisateur(absence.getUtilisateur());
	                existingAbsence.setThematique(absence.getThematique());
	                existingAbsence.setId_group(absence.getId_group());

	                savedAbsences.add(absenceRepository.save(existingAbsence));
	            } else {
	                // If no such absence exists, add it as a new absence
	                savedAbsences.add(absenceRepository.save(absence));
	            }
	        }

	        return savedAbsences;
	    }
	    public List<Absence> getAllAbsences() {
	        return absenceRepository.findAll();
	    }
	    // Method to delete absences by user ID
	    public void deleteAbsencesByUserId(Integer userId) {
	        absenceRepository.deleteByUtilisateurId(userId);
	    }
	    // Method to delete absences by thematique ID
	    public void deleteAbsencesByThematiqueId(Integer thematiqueId) {
	        absenceRepository.deleteByThematiqueId(thematiqueId);
	    }
}
