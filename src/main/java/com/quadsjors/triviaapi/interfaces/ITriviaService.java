package com.quadsjors.triviaapi.interfaces;

import com.quadsjors.triviaapi.models.AnswerCheckRequest;
import com.quadsjors.triviaapi.models.AnswerResultResponse;
import com.quadsjors.triviaapi.models.Question;

import java.util.List;

public interface ITriviaService {
    List<Question> getQuestions(int amount);
    AnswerResultResponse checkAnswers(AnswerCheckRequest request);
}
