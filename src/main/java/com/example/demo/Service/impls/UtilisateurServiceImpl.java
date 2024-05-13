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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UtilisateurServiceImpl implements UtilisateurService, UserDetailsService {
    private final UtilisateurRepository utilisateurRepository;
    private final EmailUtil emailUtil;
    private final JwtUtil jwtUtil;

    private final PasswordEncoder passwordEncoder;
    @Autowired
    public UtilisateurServiceImpl(UtilisateurRepository utilisateurRepository, JwtUtil jwtUtil, EmailUtil emailUtil, PasswordEncoder passwordEncoder) {
        this.utilisateurRepository = utilisateurRepository;
        this.jwtUtil = jwtUtil;
        this.emailUtil = emailUtil;
        this.passwordEncoder = passwordEncoder;
    }
    public boolean isUtilisateurExists(Utilisateur utilisateur) {
        return utilisateurRepository.existsByPpr(utilisateur.getPpr()) ||
                utilisateurRepository.existsByUsername(utilisateur.getUsername()) ||
                utilisateurRepository.existsByEmail(utilisateur.getEmail());
    }


    @Override
    public Utilisateur saveUtilisateur(Utilisateur utilisateur) {
        // Check if the utilisateur already exists
        if (isUtilisateurExists(utilisateur)) {
            throw new RuntimeException("Utilisateur with the same PPR, username, or email already exists.");
        }

        // Hash the password before saving the user
        String hashedPassword = passwordEncoder.encode(utilisateur.getPassword());
        utilisateur.setPassword(hashedPassword);

        // Save the utilisateur
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
        // Check if the utilisateur with the given ID exists
        Optional<Utilisateur> existingUtilisateur = utilisateurRepository.findById(utilisateur.getId());
        if (existingUtilisateur.isPresent()) {
            Utilisateur currentUtilisateur = existingUtilisateur.get();


            // Update the existing utilisateur with new data
            currentUtilisateur.setPpr(utilisateur.getPpr());
            currentUtilisateur.setNom(utilisateur.getNom());
            currentUtilisateur.setPrenom(utilisateur.getPrenom());
            currentUtilisateur.setFonction(utilisateur.getFonction());
            currentUtilisateur.setUsername(utilisateur.getUsername());
            currentUtilisateur.setEmail(utilisateur.getEmail());
            currentUtilisateur.setTel(utilisateur.getTel());
            currentUtilisateur.setGenre(utilisateur.getGenre());
            currentUtilisateur.setAdresse(utilisateur.getAdresse());
            currentUtilisateur.setDateDeNaissance(utilisateur.getDateDeNaissance());

            // Hash the password before saving the updated user (if password is changed and not null)
            String newPassword = utilisateur.getPassword();
            if (newPassword != null && !newPassword.equals(currentUtilisateur.getPassword())) {
                String hashedPassword = passwordEncoder.encode(newPassword);
                currentUtilisateur.setPassword(hashedPassword);
            }

            // Save the updated utilisateur
            return utilisateurRepository.save(currentUtilisateur);
        } else {
            // Handle the case where the utilisateur with the given ID does not exist
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
