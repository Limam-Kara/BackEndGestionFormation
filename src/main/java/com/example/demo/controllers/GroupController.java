package com.example.demo.controllers;

import com.example.demo.Service.GroupService;
import com.example.demo.Service.UtilisateurService;
import com.example.demo.model.entities.Groupe;
import com.example.demo.model.entities.Utilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/groups")
public class GroupController {

    private final GroupService groupService;
    private final UtilisateurService utilisateurService;

    @Autowired
    public GroupController(GroupService groupService, UtilisateurService utilisateurService) {
        this.groupService = groupService;
        this.utilisateurService = utilisateurService;
    }

    @PostMapping("/assignUser")
    public ResponseEntity<String> assignUserToGroup(
            @RequestParam Integer userId,
            @RequestParam Integer groupId) {
        try {

            Utilisateur utilisateur = utilisateurService.getUtilisateurById(userId);


            Groupe groupe = groupService.getGroupById(groupId);


            groupService.affectUserToGroup(utilisateur, groupe);
            return new ResponseEntity<>("User assigned to group successfully.", HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update/{groupId}")
    public ResponseEntity<String> updateGroup(
            @PathVariable Integer groupId,
            @RequestBody Groupe groupe) {
        groupe.setId(groupId);
        try {
            groupService.updateGroup(groupe);
            return new ResponseEntity<>("Group updated successfully.", HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
