package com.quadsjors.triviaapi.services;

import com.quadsjors.triviaapi.interfaces.ITriviaService;
import com.quadsjors.triviaapi.models.AnswerCheckRequest;
import com.quadsjors.triviaapi.models.AnswerResultResponse;
import com.quadsjors.triviaapi.models.Question;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TriviaService implements ITriviaService {
    @Override
    public List<Question> getQuestions(int amount) {
        return List.of();
    }

    @Override
    public AnswerResultResponse checkAnswers(AnswerCheckRequest request) {
        return null;
    }
}
