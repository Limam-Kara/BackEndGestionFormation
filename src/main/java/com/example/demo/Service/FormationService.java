package com.example.demo.Service;
import com.example.demo.model.entities.Formation;

import java.util.List;

public interface FormationService {



        Formation findById(Integer id);

        List<Formation> findAll();

        Formation save(Formation formation);

        void deleteById(Integer id);

        List<Formation> findByUtilisateurId(Integer utilisateurId);

        Formation findExistingFormation(String specialite, String niveauEtude, String diplome,Integer utilisateurId);
    }
