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

//    public void affectUserToGroup(Utilisateur utilisateur, Groupe groupe) {
//        String populationCible = groupe.getThematique().getPopulationCible();
//        if (!isValidNumericString(populationCible)) {
//            throw new RuntimeException("Invalid target population format: " + populationCible);
//        }
//
//        int targetPopulation = Integer.parseInt(populationCible);
//        if (groupe.getUtilisateurs().size() >= targetPopulation) {
//            throw new RuntimeException("La population du groupe a atteint ou dépassé la cible!");
//        }
//     // Check if the user already exists in the group
//        for (Utilisateur existingUser : groupe.getUtilisateurs()) {
//            if (existingUser.getId().equals(utilisateur.getId())) {
//                throw new RuntimeException("L’utilisateur existe déjà dans le groupe!");
//            }
//        }
//        groupe.getUtilisateurs().add(utilisateur);
//        groupeRepository.save(groupe);
//    }
    public void affectUserToGroup(Utilisateur utilisateur, Groupe groupe) {
        String populationCible = groupe.getThematique().getPopulationCible();
        if (!isValidNumericString(populationCible)) {
            throw new RuntimeException("Invalid target population format: " + populationCible);
        }

        int targetPopulation = Integer.parseInt(populationCible);
        if (groupe.getUtilisateurs().size() >= targetPopulation) {
            throw new RuntimeException("La population du groupe a atteint ou dépassé la cible!");
        }

        // Check if the user already exists in the group
        for (Utilisateur existingUser : groupe.getUtilisateurs()) {
            if (existingUser.getId().equals(utilisateur.getId())) {
                throw new RuntimeException("L’utilisateur existe déjà dans le groupe!");
            }
        }

        // Check if the user already exists in another group within the same thematic area
        List<Groupe> groupsInSameThematic = groupeRepository.findByThematiqueId(groupe.getThematique().getId());
        for (Groupe otherGroup : groupsInSameThematic) {
            if (!otherGroup.getId().equals(groupe.getId())) { // Skip the current group
                for (Utilisateur existingUser : otherGroup.getUtilisateurs()) {
                    if (existingUser.getId().equals(utilisateur.getId())) {
                        throw new RuntimeException("L’utilisateur existe déjà dans un autre groupe de la même thématique!");
                    }
                }
            }
        }

        groupe.getUtilisateurs().add(utilisateur);
        groupeRepository.save(groupe);
    }

 
 

    public void updateGroupAndAssignUser(Utilisateur utilisateur, Groupe groupe) {
    	String populationCible = groupe.getThematique().getPopulationCible();
        if (!isValidNumericString(populationCible)) {
            throw new RuntimeException("Invalid target population format: " + populationCible);
        }

        int targetPopulation = Integer.parseInt(populationCible);
        if (groupe.getUtilisateurs().size() >= targetPopulation) {
            throw new RuntimeException("La population du groupe a atteint ou dépassé la cible!");
        }

        // Check if the user already exists in the group
        for (Utilisateur existingUser : groupe.getUtilisateurs()) {
            if (existingUser.getId().equals(utilisateur.getId())) {
                throw new RuntimeException("L’utilisateur existe déjà dans le groupe!");
            }
        }

        // Check if the user already exists in another group within the same thematic area
        List<Groupe> groupsInSameThematic = groupeRepository.findByThematiqueId(groupe.getThematique().getId());
        for (Groupe otherGroup : groupsInSameThematic) {
            if (!otherGroup.getId().equals(groupe.getId())) { // Skip the current group
                for (Utilisateur existingUser : otherGroup.getUtilisateurs()) {
                    if (existingUser.getId().equals(utilisateur.getId())) {
                        // User exists in another group of the same thematic area, so remove from that group and add to the new group
                        otherGroup.getUtilisateurs().remove(existingUser);
                        groupe.getUtilisateurs().add(utilisateur);
                        groupeRepository.save(groupe);
                        groupeRepository.save(otherGroup);
                        return; // Exit the method after reassignment
                    }
                }
            }
        }

        // If user doesn't exist in any other group within the same thematic area, simply add to the new group
        groupe.getUtilisateurs().add(utilisateur);
        groupeRepository.save(groupe);
    }

    private boolean isValidNumericString(String str) {
        if (str == null) {
            return false;
        }
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

//    @Override
//    public void updateGroup(Groupe groupe) {
//        Thematique thematique = groupe.getThematique();
//        int totalPopulation = groupeRepository.getTotalPopulationByThematiqueId(thematique.getId());
//        if (totalPopulation >= Integer.parseInt(thematique.getPopulationCible())) {
//            throw new RuntimeException("Total population of groups exceeds or meets the target population for the theme.");
//        }
//
//        groupeRepository.save(groupe);
//    }
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
    }
    @Override
    public List<Groupe> getAllGroups() {
        return groupeRepository.findAll(); // Fetch all groups from repository

    }
    @Override
    public void removeUserFromGroup(Utilisateur utilisateur, Groupe groupe) {
        if (groupe.getUtilisateurs().contains(utilisateur)) {
            groupe.getUtilisateurs().remove(utilisateur);
            groupeRepository.save(groupe);
        } else {
            throw new RuntimeException("L’utilisateur ne fait pas partie du groupe\r\n");
        }}

	@Override
	public void updateGroup(Groupe groupe) {
		// TODO Auto-generated method stub
		
	}

}
