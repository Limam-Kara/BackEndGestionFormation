package com.example.demo.model.repositories;


import com.example.demo.model.entities.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Integer> {
    public Optional<Utilisateur> findByUsername(String username);
    public Optional<Utilisateur> findById(Integer id);

    Optional<Utilisateur> findByEmail(String email);
    boolean existsByPpr(String ppr);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

 

}
