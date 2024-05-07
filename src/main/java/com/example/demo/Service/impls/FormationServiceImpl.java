package com.example.demo.Service.impls;

import com.example.demo.model.entities.Formation;

import com.example.demo.Service.FormationService;
import com.example.demo.model.repositories.FormationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class FormationServiceImpl implements FormationService {

    private final FormationRepository formationRepository;

    @Autowired
    public FormationServiceImpl(FormationRepository formationRepository) {
        this.formationRepository = formationRepository;
    }

    @Override
    public Formation findById(Integer id) {
        Optional<Formation> optionalFormation = formationRepository.findById(id);
        return optionalFormation.orElse(null);
    }

    @Override
    public List<Formation> findAll() {
        return formationRepository.findAll();
    }

    @Override
    public Formation save(Formation formation) {
        // Check if a similar Formation already exists based on specialite, niveauEtude, diplome, and utilisateur ID
        Formation existingFormation = findExistingFormation(
                formation.getSpecialite(),
                formation.getNiveauEtude(),
                formation.getDiplome(),
                formation.getUtilisateur().getId());

        if (existingFormation != null) {
            throw new RuntimeException("Formation already exists with the same criteria.");
        }

        // Save the new Formation
        return formationRepository.save(formation);
    }

    @Override
    public Formation findExistingFormation(String specialite, String niveauEtude, String diplome, Integer utilisateurId) {
        Optional<Formation> optionalFormation = formationRepository.findBySpecialiteAndNiveauEtudeAndDiplomeAndUtilisateur_Id(
                specialite, niveauEtude, diplome, utilisateurId);
        return optionalFormation.orElse(null); // Return the Formation if present, otherwise return null
    }



    @Override
    public void deleteById(Integer id) {
        formationRepository.deleteById(id);
    }

    @Override
    public List<Formation> findByUtilisateurId(Integer utilisateurId) {
        return formationRepository.findByUtilisateurId(utilisateurId);
    }




}
