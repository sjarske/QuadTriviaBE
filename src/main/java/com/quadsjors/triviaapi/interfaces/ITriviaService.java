package com.quadsjors.triviaapi.interfaces;

import com.quadsjors.triviaapi.models.AnswerRequest;
import com.quadsjors.triviaapi.models.AnswerResult;
import com.quadsjors.triviaapi.models.Question;

import java.util.List;

public interface ITriviaService {
    List<Question> getQuestions();
    AnswerResult checkAnswers(AnswerRequest request);
    void clearCache();
}
