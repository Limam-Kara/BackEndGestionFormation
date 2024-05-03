package com.example.demo.model.repositories;

import com.example.demo.model.entities.Thematique;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ThematiqueRepository extends JpaRepository<Thematique, Integer> {
}