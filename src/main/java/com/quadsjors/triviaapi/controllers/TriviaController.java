package com.quadsjors.triviaapi.controllers;

import com.quadsjors.triviaapi.interfaces.ITriviaService;
import com.quadsjors.triviaapi.models.AnswerRequest;
import com.quadsjors.triviaapi.models.AnswerResult;
import com.quadsjors.triviaapi.models.Question;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "https://incredible-bavarois-e4bb41.netlify.app")
@RestController
@RequestMapping("/api")
public class TriviaController {

    private final ITriviaService triviaService;

    public TriviaController(ITriviaService triviaService) {
        this.triviaService = triviaService;
    }

    @GetMapping("/questions")
    public List<Question> getQuestions() {
        return triviaService.getQuestions();
    }

    @PostMapping("/checkanswers")
    public AnswerResult checkAnswers(@RequestBody AnswerRequest request) {
        return triviaService.checkAnswers(request);
    }

    @PostMapping("/newquiz")
    public void newQuiz() {
        triviaService.clearCache();
    }
}
