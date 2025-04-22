package com.quadsjors.triviaapi.helpers;

import com.fasterxml.jackson.databind.JsonNode;
import com.quadsjors.triviaapi.models.Question;
import org.springframework.web.client.RestTemplate;

import java.util.*;

public class TriviaHelper {

    public static boolean shouldUseCache(List<Question> cachedQuestions, long lastFetchTime, long cacheDurationMs) {
        long now = System.currentTimeMillis();
        return cachedQuestions != null && (now - lastFetchTime) < cacheDurationMs;
    }

    public static JsonNode fetchQuestionsFromAPI(RestTemplate restTemplate) {
        String url = "https://opentdb.com/api.php?amount=5&type=multiple";
        return restTemplate.getForObject(url, JsonNode.class);
    }

    public static boolean isValidResponse(JsonNode root) {
        return root != null &&
                root.has("response_code") &&
                root.get("response_code").asInt() == 0 &&
                root.has("results") &&
                root.get("results").isArray();
    }

    public static List<Question> parseQuestions(JsonNode results, Map<String, String> correctAnswers) {
        List<Question> questions = new ArrayList<>();

        for (JsonNode item : results) {
            String id = UUID.randomUUID().toString();
            String questionText = item.get("question").asText();
            String correct = item.get("correct_answer").asText();

            List<String> allAnswers = new ArrayList<>();
            allAnswers.add(correct);
            for (JsonNode inc : item.get("incorrect_answers")) {
                allAnswers.add(inc.asText());
            }
            Collections.shuffle(allAnswers);

            Question q = new Question();
            q.id = id;
            q.question = questionText;
            q.answers = allAnswers;

            questions.add(q);
            correctAnswers.put(id, correct);
        }

        return questions;
    }
}
