package com.example.demo.Service.impls;

import com.example.demo.Service.TokenService;
import com.example.demo.model.entities.Token;
import com.example.demo.model.entities.Utilisateur;
import com.example.demo.model.repositories.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TokenServiceImpl implements TokenService {
    private final TokenRepository tokenRepository;

    @Autowired
    public TokenServiceImpl(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    public void saveToken(String jwt, Utilisateur user) {
        Token token = new Token();
        token.setToken(jwt);
        token.setLoggedOut(false);
        token.setUtilisateur(user);
        tokenRepository.save(token);
    }
    public void revokeAllTokensByUser(Utilisateur user) {
        List<Token> validTokens = tokenRepository.findAllTokensByUser(user.getId());
        if (validTokens.isEmpty()) {
            return; // No tokens found, no action needed
        }
        validTokens.forEach(t -> {
            t.setLoggedOut(true);
        });

        tokenRepository.saveAll(validTokens);
    }

}
