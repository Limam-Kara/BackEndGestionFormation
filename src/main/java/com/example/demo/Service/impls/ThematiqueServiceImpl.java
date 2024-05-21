package com.example.demo.Service.impls;

import com.example.demo.Service.ThematiqueService;
import com.example.demo.model.entities.Groupe;
import com.example.demo.model.entities.Thematique;

import com.example.demo.model.repositories.GroupeRepository;
import com.example.demo.model.repositories.ThematiqueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ThematiqueServiceImpl implements ThematiqueService {

    private final ThematiqueRepository thematiqueRepository;
    private final GroupeRepository groupeRepository;
    
    
    public Thematique getThematiqueByGroupeId(Integer groupId) {
        return thematiqueRepository.findThematiqueByGroupeId(groupId);
    }
    
    public ThematiqueServiceImpl(ThematiqueRepository thematiqueRepository, GroupeRepository groupeRepository) {
        this.thematiqueRepository = thematiqueRepository;
        this.groupeRepository = groupeRepository;
    }

    @Override
    public Thematique findById(Integer id) {
        Optional<Thematique> optionalThematique = thematiqueRepository.findById(id);
        return optionalThematique.orElse(null); // Handle if Thematique with given id not found
    }

    @Override
    public List<Thematique> findAll() {
        return thematiqueRepository.findAll();
    }

    @Override
    public Thematique save(Thematique thematique) {
        try {
            Thematique savedThematique = thematiqueRepository.save(thematique);
            createGroupsForThematique(savedThematique); // Create groups after saving the thematique
            return savedThematique;        } catch (Exception e) {
            // Handle specific exceptions here (e.g., DataIntegrityViolationException)
            throw new RuntimeException("Failed to save Thematique: " + e.getMessage());
        }
    }

    @Override
    public void deleteById(Integer id) {
        try {
        	
            thematiqueRepository.deleteById(id);
        } catch (Exception e) {
            // Handle specific exceptions here (e.g., EmptyResultDataAccessException)
            throw new RuntimeException("Failed to delete Thematique with id " + id + ": " + e.getMessage());
        }
    }
    @Override
    public void createGroupsForThematique(Thematique thematique) {
        int nbrGroupe = thematique.getNbrGroupe();
        List<Groupe> groupes = new ArrayList<>();

        for (int i = 1; i <= nbrGroupe; i++) {
            Groupe groupe = new Groupe();
            groupe.setNumGroupe("Groupe " + i);
            groupe.setThematique(thematique);
            groupes.add(groupe);
        }

        groupeRepository.saveAll(groupes);
    }
    @Override
    @Transactional
    public Thematique update(Thematique updatedThematique) {
        Integer thematiqueId = updatedThematique.getId();

        // Find the existing Thematique entity by ID
        Thematique existingThematique = findById(thematiqueId);
        if (existingThematique == null) {
            throw new RuntimeException("Thematique with ID " + thematiqueId + " not found.");
        }

        // Update the Thematique entity properties
        existingThematique.setCoutLogistique(updatedThematique.getCoutLogistique());
        existingThematique.setCoutPedagogique(updatedThematique.getCoutPedagogique());
        existingThematique.setDateDebut(updatedThematique.getDateDebut());
        existingThematique.setDateFin(updatedThematique.getDateFin());
        existingThematique.setDomaineFormation(updatedThematique.getDomaineFormation());
        existingThematique.setIntitule(updatedThematique.getIntitule());
        existingThematique.setNbrFormateurExtr(updatedThematique.getNbrFormateurExtr());
        existingThematique.setNbrFormateurIntr(updatedThematique.getNbrFormateurIntr());
        existingThematique.setNbrJoursFormation(updatedThematique.getNbrJoursFormation());
        existingThematique.setObjectif(updatedThematique.getObjectif());
        existingThematique.setPopulationCible(updatedThematique.getPopulationCible());
        existingThematique.setPrestataire(updatedThematique.getPrestataire());
        existingThematique.setNbrGroupe(updatedThematique.getNbrGroupe());

        // Update associated Groupe entities based on nbrGroupe change
        updateGroupeEntities(existingThematique);

        // Save and return the updated Thematique entity
        return thematiqueRepository.save(existingThematique);
    }

    private void updateGroupeEntities(Thematique thematique) {
        int updatedNbrGroupe = thematique.getNbrGroupe();
        List<Groupe> groupes = thematique.getGroupes();

        // Remove excess groupes if nbrGroupe is reduced
        if (updatedNbrGroupe < groupes.size()) {
            List<Groupe> groupesToRemove = groupes.subList(updatedNbrGroupe, groupes.size());
            groupeRepository.deleteAll(groupesToRemove);
            groupes.removeAll(groupesToRemove);
        }
        // Add new groupes if nbrGroupe is increased
        else if (updatedNbrGroupe > groupes.size()) {
            for (int i = groupes.size() + 1; i <= updatedNbrGroupe; i++) {
                Groupe groupe = new Groupe();
                groupe.setNumGroupe("Groupe " + i);
                groupe.setThematique(thematique);
                groupes.add(groupe);
            }
            groupeRepository.saveAll(groupes.subList(groupes.size() - (updatedNbrGroupe - groupes.size()), groupes.size()));
        }
        // No action needed if nbrGroupe is unchanged
    }

}
