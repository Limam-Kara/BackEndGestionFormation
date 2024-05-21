package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Service.EvaluationService;
import com.example.demo.model.entities.Evaluation;
import com.example.demo.model.entities.Q_Reponse;

@RestController
@RequestMapping("/evaluations")
public class EvaluationController {
	@Autowired
    private EvaluationService evaluationService;

	 @PostMapping("/add")
	    public ResponseEntity<?> addEvaluation(@RequestParam Integer userId,
	                                           @RequestParam Integer thematiqueId,
	                                           @RequestBody List<String> responses) {
	        try {
	            Evaluation evaluation = evaluationService.addEvaluationForUser(userId, thematiqueId, responses);
	            return ResponseEntity.status(HttpStatus.CREATED).body(evaluation);
	        } catch (RuntimeException e) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
	        }
	    }

	    @ExceptionHandler(RuntimeException.class)
	    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
	    }

    @GetMapping("/{evaluationId}/responses")
    public ResponseEntity<List<Q_Reponse>> getResponses(@PathVariable Integer evaluationId) {
        try {
            List<Q_Reponse> responses = evaluationService.getResponsesForEvaluation(evaluationId);
            return new ResponseEntity<>(responses, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/evaluations")
    public List<Evaluation> getAllEvaluations() {
        return evaluationService.getAllEvaluations();
    }
}
