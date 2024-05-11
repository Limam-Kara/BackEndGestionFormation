package com.example.demo.Service.impls;

import com.example.demo.Service.GroupService;
import com.example.demo.model.entities.Groupe;
import com.example.demo.model.entities.Thematique;
import com.example.demo.model.entities.Utilisateur;
import com.example.demo.model.repositories.GroupeRepository;
import com.example.demo.model.repositories.ThematiqueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GroupServiceImpl implements GroupService {

    private final GroupeRepository groupeRepository;
    private final ThematiqueRepository thematiqueRepository;

    @Autowired
    public GroupServiceImpl(GroupeRepository groupeRepository, ThematiqueRepository thematiqueRepository) {
        this.groupeRepository = groupeRepository;
        this.thematiqueRepository = thematiqueRepository;
    }

    public void affectUserToGroup(Utilisateur utilisateur, Groupe groupe) {
        String populationCible = groupe.getThematique().getPopulationCible();
        if (!isValidNumericString(populationCible)) {
            throw new RuntimeException("Invalid target population format: " + populationCible);
        }

        int targetPopulation = Integer.parseInt(populationCible);
        if (groupe.getUtilisateurs().size() >= targetPopulation) {
            throw new RuntimeException("Group population has reached or exceeded the target.");
        }

        groupe.getUtilisateurs().add(utilisateur);
        groupeRepository.save(groupe);
    }

    private boolean isValidNumericString(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }


    @Override
    public void updateGroup(Groupe groupe) {
        Thematique thematique = groupe.getThematique();
        int totalPopulation = groupeRepository.getTotalPopulationByThematiqueId(thematique.getId());
        if (totalPopulation >= Integer.parseInt(thematique.getPopulationCible())) {
            throw new RuntimeException("Total population of groups exceeds or meets the target population for the theme.");
        }

        groupeRepository.save(groupe);
    }
    @Override
    public Groupe getGroupById(Integer id) {
        Optional<Groupe> optionalGroupe = groupeRepository.findById(id);
        return optionalGroupe.orElseThrow(() -> new RuntimeException("Group not found with ID: " + id));
    }

    public List<Utilisateur> getAllUsersInGroup(Integer groupId) {
        Optional<Groupe> groupeOptional = groupeRepository.findById(groupId);
        if (groupeOptional.isPresent()) {
            Groupe groupe = groupeOptional.get();
            return groupe.getUtilisateurs();
        } else {
            // Handle the case where the group with the given id doesn't exist
            throw new RuntimeException("Group not found with id: " + groupId);
        }

    @Override
    public List<Groupe> getAllGroups() {
        return groupeRepository.findAll(); // Fetch all groups from repository

    }

}
