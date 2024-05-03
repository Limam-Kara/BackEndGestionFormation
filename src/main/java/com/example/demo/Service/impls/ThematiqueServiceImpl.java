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

}
