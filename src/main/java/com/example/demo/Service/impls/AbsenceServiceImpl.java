package com.example.demo.Service.impls;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.entities.Absence;
import com.example.demo.model.repositories.AbsenceRepository;

@Service
public class AbsenceServiceImpl {

	 @Autowired
	    private AbsenceRepository absenceRepository;

	    public List<Absence> saveAbsences(List<Absence> absences) {
	        return absenceRepository.saveAll(absences);
	    }
}
