package com.example.demo.Service.impls;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Service.EvaluationService;
import com.example.demo.model.entities.Evaluation;
import com.example.demo.model.entities.Q_Reponse;
import com.example.demo.model.entities.Question;
import com.example.demo.model.entities.Thematique;
import com.example.demo.model.entities.Utilisateur;
import com.example.demo.model.repositories.EvaluationRepository;
import com.example.demo.model.repositories.Q_ReponseRepository;
import com.example.demo.model.repositories.QuestionRepository;
import com.example.demo.model.repositories.UtilisateurRepository;

@Service
public class EvaluationServiceImpl implements EvaluationService {

    @Autowired
    private EvaluationRepository evaluationRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private Q_ReponseRepository qReponseRepository;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Override
    public Evaluation addEvaluationForUser(Integer userId, Integer thematiqueId, List<String> responses) {
        // Fetch user and thematique
        Utilisateur utilisateur = utilisateurRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));
        Thematique thematique = new Thematique();
        thematique.setId(thematiqueId); // Assuming thematique exists and is set correctly

        // Check if the user has already evaluated this thematique
        Evaluation existingEvaluation = evaluationRepository.findByBeneficiareAndThematique(utilisateur, thematique);
        if (existingEvaluation != null) {
            throw new RuntimeException("Déjà évalué cette thématique");
        }

        // Create new evaluation
        Evaluation evaluation = new Evaluation();
        evaluation.setBeneficiare(utilisateur);
        evaluation.setThematique(thematique);
        evaluation = evaluationRepository.save(evaluation);

        // Get all questions
        List<Question> questions = questionRepository.findAll();
        if (questions.size() != responses.size()) {
            throw new RuntimeException("Le nombre de réponses ne correspond pas au nombre de questions");
        }

        // Create and save responses
        for (int i = 0; i < questions.size(); i++) {
            Q_Reponse qReponse = new Q_Reponse();
            qReponse.setQuestion(questions.get(i));
            qReponse.setEvaluation(evaluation);
            qReponse.setReponse(responses.get(i));
            qReponseRepository.save(qReponse);
        }

        return evaluation;
    }

    @Override
    public List<Q_Reponse> getResponsesForEvaluation(Integer evaluationId) {
        return qReponseRepository.findAllByEvaluationId(evaluationId);
    }

    @Override
    public List<Evaluation> getAllEvaluations() {
        return evaluationRepository.findAll();
    }

}
