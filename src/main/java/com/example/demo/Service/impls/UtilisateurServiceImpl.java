package com.example.demo.Service.impls;

import com.example.demo.Service.UtilisateurService;
import com.example.demo.model.entities.Utilisateur;
import com.example.demo.model.repositories.UtilisateurRepository;
import com.example.demo.utils.EmailUtil;
import com.example.demo.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UtilisateurServiceImpl implements UtilisateurService, UserDetailsService {
    private final UtilisateurRepository utilisateurRepository;
    private final EmailUtil emailUtil;
    private final JwtUtil jwtUtil;


    @Autowired
    public UtilisateurServiceImpl(UtilisateurRepository utilisateurRepository, JwtUtil jwtUtil, EmailUtil emailUtil) {
        this.utilisateurRepository = utilisateurRepository;
        this.jwtUtil = jwtUtil;
        this.emailUtil = emailUtil;

    }

    @Override
    public Utilisateur saveUtilisateur(Utilisateur utilisateur) {
        // Hash the password before saving the user
//        String hashedPassword = passwordEncoder.encode(utilisateur.getPassword());
//        utilisateur.setPassword(hashedPassword);
        return utilisateurRepository.save(utilisateur);
    }

    @Override
    public Utilisateur getUtilisateurByUsername(String username) {
        Optional<Utilisateur> optionalUtilisateur = utilisateurRepository.findByUsername(username);
        return optionalUtilisateur.orElse(null);
    }
    public Utilisateur getUtilisateurById(Integer id) {
        Optional<Utilisateur> optionalUtilisateur = utilisateurRepository.findById(id);
        return optionalUtilisateur.orElse(null);
    }

    @Override
    public List<Utilisateur> getAllUtilisateurs() {
        return utilisateurRepository.findAll();
    }

    @Override
    public Utilisateur editUtilisateur(Utilisateur utilisateur) {
        // Check if the user exists
        if (utilisateurRepository.existsById(utilisateur.getId())) {
            // Save the updated user
            return utilisateurRepository.save(utilisateur);
        } else {
            // Handle the case where the user does not exist
            throw new RuntimeException("Utilisateur with ID " + utilisateur.getId() + " does not exist.");
        }
    }

    @Override
    public void deleteUtilisateur(Integer id) {
        utilisateurRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return utilisateurRepository.findByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException("User not found"));
    }

}
