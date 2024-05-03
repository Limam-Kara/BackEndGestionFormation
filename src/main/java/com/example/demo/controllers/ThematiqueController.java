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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteThematiqueById(@PathVariable Integer id) {
        thematiqueService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
