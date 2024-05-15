package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Service.impls.AbsenceServiceImpl;
import com.example.demo.model.entities.Absence;

@RestController
	@RequestMapping("/absences")
	public class AbsenceController {

	    @Autowired
	    private AbsenceServiceImpl absenceService;

	    @PostMapping("/save")
	    public ResponseEntity<List<Absence>> saveAbsences(@RequestBody List<Absence> absences) {
	        try {
	            List<Absence> savedAbsences = absenceService.saveAbsences(absences);
	            return new ResponseEntity<>(savedAbsences, HttpStatus.CREATED);
	        } catch (Exception e) {
	            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    }
	    @GetMapping("/all")
	    public ResponseEntity<List<Absence>> getAllAbsences() {
	        try {
	            List<Absence> absences = absenceService.getAllAbsences();
	            return new ResponseEntity<>(absences, HttpStatus.OK);
	        } catch (Exception e) {
	            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    }
	    // Endpoint pour récupérer toutes les absences, supprimer, mettre à jour, etc.
	}

