package com.quadsjors.triviaapi.services;

import com.fasterxml.jackson.databind.JsonNode;
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
        long now = System.currentTimeMillis();
        if (cachedQuestions != null && now - lastFetchTime < CACHE_DURATION_MS) {
            return cachedQuestions;
        }

        String url = "https://opentdb.com/api.php?amount=5&type=multiple";
        JsonNode root = restTemplate.getForObject(url, JsonNode.class);

        if (root.get("response_code") == null || root.get("response_code").asInt() != 0) {
            System.out.println("⚠️ API fout: " + root.toPrettyString());
            return cachedQuestions != null ? cachedQuestions : List.of();
        }

        JsonNode results = root.get("results");
        if (results == null || !results.isArray()) {
            return List.of();
        }

        List<Question> questions = new ArrayList<>();
        for (JsonNode item : results) {
            String questionId = UUID.randomUUID().toString();

            String question = item.get("question").asText();
            String correct = item.get("correct_answer").asText();
            List<String> allAnswers = new ArrayList<>();
            allAnswers.add(correct);

            for (JsonNode inc : item.get("incorrect_answers")) {
                allAnswers.add(inc.asText());
            }

            Collections.shuffle(allAnswers);
            correctAnswers.put(questionId, correct);

            Question dto = new Question();
            dto.id = questionId;
            dto.question = question;
            dto.answers = allAnswers;
            questions.add(dto);
        }

        // Cache reset
        cachedQuestions = questions;
        lastFetchTime = now;
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
