package com.example.demo.Service;

import java.util.List;

import com.example.demo.model.entities.Evaluation;
import com.example.demo.model.entities.Q_Reponse;

public interface EvaluationService {
	   Evaluation addEvaluationForUser(Integer userId, Integer thematiqueId, List<String> responses);
	    List<Q_Reponse> getResponsesForEvaluation(Integer evaluationId);
	    List<Evaluation> getAllEvaluations(); 

}
