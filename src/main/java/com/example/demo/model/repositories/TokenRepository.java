package com.example.demo.model.repositories;

import com.example.demo.model.entities.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Integer> {
    @Query("""
                select t from Token t inner join Utilisateur u on t.utilisateur.id = u.id
                where t.utilisateur.id = :userId and t.loggedOut = false
           """)
    List<Token> findAllTokensByUser(Integer userId);

    Optional<Token> findByToken(String token);
}
