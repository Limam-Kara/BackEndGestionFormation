package com.example.demo.Service;

import com.example.demo.model.entities.Groupe;
import com.example.demo.model.entities.Utilisateur;

import java.util.List;
import java.util.Optional;

public interface GroupService {

    void affectUserToGroup(Utilisateur utilisateur, Groupe groupe);

    void updateGroup(Groupe groupe);

    Groupe getGroupById(Integer id);
 
	List<Utilisateur> getAllUsersInGroup(Integer groupId);
     List<Groupe> getAllGroups();
     void removeUserFromGroup(Utilisateur utilisateur, Groupe groupe);

}
