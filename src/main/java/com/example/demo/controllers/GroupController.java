package com.example.demo.controllers;

import com.example.demo.Service.GroupService;
import com.example.demo.Service.UtilisateurService;
import com.example.demo.model.entities.Groupe;
import com.example.demo.model.entities.Utilisateur;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
            return new ResponseEntity<>("Bénéficiare affecté au groupe avec succès .", HttpStatus.OK);
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

    @GetMapping("/{groupId}/users")
    public ResponseEntity<List<Utilisateur>> getUsersInGroup(@PathVariable Integer groupId) {
        try {
            List<Utilisateur> usersInGroup = groupService.getAllUsersInGroup(groupId);
            return new ResponseEntity<>(usersInGroup, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // or any appropriate error response
        }
    }
    @GetMapping("/all")
    public ResponseEntity<List<Groupe>> getAllGroups() {
        try {
            List<Groupe> groups = groupService.getAllGroups();
            return new ResponseEntity<>(groups, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/{groupId}")
    public ResponseEntity<?> getGroupById(@PathVariable Integer groupId) {
        try {
            Groupe group = groupService.getGroupById(groupId);
            if (group != null) {
                return new ResponseEntity<>(group, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Group not found with ID: " + groupId, HttpStatus.NOT_FOUND);
            }
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
