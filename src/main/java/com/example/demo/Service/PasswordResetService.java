package com.example.demo.Service;

import com.example.demo.model.entities.Utilisateur;
import com.example.demo.model.repositories.UtilisateurRepository;
import com.example.demo.utils.EmailUtil;
import com.example.demo.utils.JwtUtil;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordResetService {
    private final UtilisateurRepository utilisateurRepository;
    private final JwtUtil jwtUtil;
    private final EmailUtil emailUtil;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public PasswordResetService(UtilisateurRepository utilisateurRepository, JwtUtil jwtUtil, EmailUtil emailUtil, PasswordEncoder passwordEncoder) {
        this.utilisateurRepository = utilisateurRepository;
        this.jwtUtil = jwtUtil;
        this.emailUtil = emailUtil;
        this.passwordEncoder = passwordEncoder;

    }

    public String initiatePasswordReset(String email) {
        Utilisateur utilisateur = utilisateurRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User with email " + email + " not found"));

        // Generate a JWT token with email and set expiration time to 5 minutes
        String token = jwtUtil.generateTokenWithEmail(email, 5 * 60 * 1000);

        // Send the password reset email containing the token
        try {
            emailUtil.sendResetPasswordEmail(email, token);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send password reset email to " + email, e);
        }

        return "Password reset instructions have been sent to your email.";
    }

    public String resetPassword(String token, String newPassword) {
        // Validate the token
        if (!jwtUtil.validateToken(token)) {
            throw new RuntimeException("Invalid or expired token");
        }

        // Extract email from the token
        String email = jwtUtil.extractUsername(token);

        // Find user by email
        Utilisateur utilisateur = utilisateurRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User with email " + email + " not found"));

        // Hash the new password before saving
        String hashedPassword = passwordEncoder.encode(newPassword);
        utilisateur.setPassword(hashedPassword);
        utilisateurRepository.save(utilisateur);

        return "Password has been reset successfully";
    }



}