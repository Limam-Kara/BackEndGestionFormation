package com.example.demo.Service;

import com.example.demo.model.entities.Thematique;

import java.util.List;

public interface ThematiqueService {

    Thematique findById(Integer id);

    List<Thematique> findAll();

    Thematique save(Thematique thematique);

    void deleteById(Integer id);
    void createGroupsForThematique(Thematique thematique);
    public Thematique update(Thematique updatedThematique);

	Thematique getThematiqueByGroupeId(Integer groupeId);
}

