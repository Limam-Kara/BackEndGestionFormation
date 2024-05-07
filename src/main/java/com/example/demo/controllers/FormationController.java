package com.example.demo.controllers;

import com.example.demo.model.entities.Formation;
import com.example.demo.Service.FormationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/formations")
public class FormationController {

    private final FormationService formationService;

    @Autowired
    public FormationController(FormationService formationService) {
        this.formationService = formationService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Formation> getFormationById(@PathVariable Integer id) {
        Formation formation = formationService.findById(id);
        if (formation != null) {
            return new ResponseEntity<>(formation, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Formation>> getAllFormations() {
        List<Formation> formations = formationService.findAll();
        return new ResponseEntity<>(formations, HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<Formation> saveFormation(@RequestBody Formation formation) {
        try {
            Formation savedFormation = formationService.save(formation);
            return new ResponseEntity<>(savedFormation, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            // Log the exception for troubleshooting
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFormationById(@PathVariable Integer id) {
        formationService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/utilisateur/{utilisateurId}")
    public ResponseEntity<List<Formation>> getFormationsByUtilisateurId(@PathVariable Integer utilisateurId) {
        List<Formation> formations = formationService.findByUtilisateurId(utilisateurId);
        if (formations != null && !formations.isEmpty()) {
            return new ResponseEntity<>(formations, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
