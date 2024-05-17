package com.example.demo.Service;

import java.util.List;
import java.util.Optional;

import com.example.demo.model.entities.Question;

public interface  QuestionService {
	Question createQuestion(Question question);
    Optional<Question> getQuestionById(Integer id);
    List<Question> getAllQuestions();
    Question updateQuestion(Integer id, Question question);
    void deleteQuestion(Integer id);
}
