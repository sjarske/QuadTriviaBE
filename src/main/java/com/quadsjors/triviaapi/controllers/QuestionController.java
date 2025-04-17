package com.quadsjors.triviaapi.controllers;

import com.quadsjors.triviaapi.interfaces.ITriviaService;
import com.quadsjors.triviaapi.models.AnswerCheckRequest;
import com.quadsjors.triviaapi.models.AnswerResultResponse;
import com.quadsjors.triviaapi.models.Question;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class QuestionController {

    private final ITriviaService triviaService;

    public QuestionController(ITriviaService triviaService) {
        this.triviaService = triviaService;
    }

    @GetMapping("/questions")
    public List<Question> getQuestions(@RequestParam(defaultValue = "5") int amount) {
        return triviaService.getQuestions(amount);
    }

    @PostMapping("/checkanswers")
    public AnswerResultResponse checkAnswers(@RequestBody AnswerCheckRequest request) {
        return triviaService.checkAnswers(request);
    }
}
