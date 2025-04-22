package com.quadsjors.triviaapi.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.quadsjors.triviaapi.helpers.TriviaHelper;
import com.quadsjors.triviaapi.interfaces.ITriviaService;
import com.quadsjors.triviaapi.models.AnswerFeedback;
import com.quadsjors.triviaapi.models.AnswerRequest;
import com.quadsjors.triviaapi.models.AnswerResult;
import com.quadsjors.triviaapi.models.Question;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.util.*;

@Service
public class TriviaService implements ITriviaService {
    private final Map<String, String> correctAnswers = new HashMap<>();
    private final RestTemplate restTemplate = new RestTemplate();
    private List<Question> cachedQuestions = null;
    private long lastFetchTime = 0;
    private final long CACHE_DURATION_MS = 30_000; // 30 seconden

    @Override
    public List<Question> getQuestions() {
        if (TriviaHelper.shouldUseCache(cachedQuestions, lastFetchTime, CACHE_DURATION_MS)) {
            return cachedQuestions;
        }

        JsonNode root = TriviaHelper.fetchQuestionsFromAPI(restTemplate);
        if (!TriviaHelper.isValidResponse(root)) {
            return cachedQuestions != null ? cachedQuestions : List.of();
        }

        List<Question> questions = TriviaHelper.parseQuestions(root.get("results"), correctAnswers);
        cachedQuestions = questions;
        lastFetchTime = System.currentTimeMillis();
        return questions;
    }


    public void clearCache() {
        cachedQuestions = null;
        lastFetchTime = 0;
    }

    @Override
    public AnswerResult checkAnswers(AnswerRequest request) {
        Map<String, AnswerFeedback> resultMap = new HashMap<>();
        request.answers.forEach((questionId, userAnswer) -> {
            String correct = correctAnswers.get(questionId);
            boolean correctBool = correct != null && correct.equals(userAnswer);

            AnswerFeedback feedback = new AnswerFeedback();
            feedback.isCorrect = correctBool;
            feedback.correctAnswer = correct;
            feedback.userAnswer = userAnswer;

            resultMap.put(questionId, feedback);
        });

        AnswerResult result = new AnswerResult();
        result.results = resultMap;
        return result;
    }
}
