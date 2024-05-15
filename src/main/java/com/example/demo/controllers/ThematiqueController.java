package com.example.demo.controllers;

import com.example.demo.model.entities.Thematique;
import com.example.demo.Service.ThematiqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/thematiques")
public class ThematiqueController {

    @Autowired
    private ThematiqueService thematiqueService;
    
    @GetMapping("/{thematique_id}/thematique")
    public ResponseEntity<Thematique> getThematiqueByGroupeId(@PathVariable Integer thematique_id) {
        Thematique thematique = thematiqueService.getThematiqueByGroupeId(thematique_id);
        if (thematique != null) {
            return new ResponseEntity<>(thematique, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<Thematique> getThematiqueById(@PathVariable Integer id) {
        Thematique thematique = thematiqueService.findById(id);
        if (thematique != null) {
            return new ResponseEntity<>(thematique, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Thematique>> getAllThematiques() {
        List<Thematique> thematiques = thematiqueService.findAll();
        return new ResponseEntity<>(thematiques, HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<Thematique> saveThematique(@RequestBody Thematique thematique) {
        Thematique savedThematique = thematiqueService.save(thematique);
        return new ResponseEntity<>(savedThematique, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Thematique> updateThematique(@PathVariable Integer id, @RequestBody Thematique updatedThematique) {
        try {
            updatedThematique.setId(id); // Ensure the ID is set for the updated entity
            Thematique savedThematique = thematiqueService.update(updatedThematique);
            return new ResponseEntity<>(savedThematique, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteThematiqueById(@PathVariable Integer id) {
        thematiqueService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
