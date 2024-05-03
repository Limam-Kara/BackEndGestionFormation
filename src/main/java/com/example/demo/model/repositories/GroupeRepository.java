package com.example.demo.model.repositories;

import com.example.demo.model.entities.Groupe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupeRepository extends JpaRepository<Groupe, Integer> {
}