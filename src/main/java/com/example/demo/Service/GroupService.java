package com.example.demo.Service;

import com.example.demo.model.entities.Groupe;
import com.example.demo.model.entities.Utilisateur;

import java.util.Optional;

public interface GroupService {

    void affectUserToGroup(Utilisateur utilisateur, Groupe groupe);

    void updateGroup(Groupe groupe);

    public Groupe getGroupById(Integer id);

}
