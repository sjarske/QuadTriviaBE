package com.quadsjors.triviaapi.helpers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.quadsjors.triviaapi.models.Question;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class TriviaHelperTest {

    @Test
    void testShouldUseCache_true() {
        List<Question> cached = List.of(new Question());
        long now = System.currentTimeMillis();
        long cacheDuration = 30_000;

        boolean result = TriviaHelper.shouldUseCache(cached, now - 10_000, cacheDuration);

        assertTrue(result);
    }

    @Test
    void testShouldUseCache_false_dueToTime() {
        List<Question> cached = List.of(new Question());
        long now = System.currentTimeMillis();
        long cacheDuration = 30_000;

        boolean result = TriviaHelper.shouldUseCache(cached, now - 40_000, cacheDuration);

        assertFalse(result);
    }

    @Test
    void testShouldUseCache_false_dueToNullCache() {
        boolean result = TriviaHelper.shouldUseCache(null, System.currentTimeMillis(), 30_000);

        assertFalse(result);
    }

    @Test
    void testIsValidResponse_true() throws Exception {
        String json = """
                {
                  "response_code": 0,
                  "results": []
                }
                """;
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(json);

        assertTrue(TriviaHelper.isValidResponse(root));
    }

    @Test
    void testIsValidResponse_false_missingFields() throws Exception {
        String json = """
                {
                  "response_code": 1
                }
                """;
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(json);

        assertFalse(TriviaHelper.isValidResponse(root));
    }

    @Test
    void testParseQuestions_returnsCorrectSize() throws Exception {
        String json = """
                [
                  {
                    "question": "What is 2+2?",
                    "correct_answer": "4",
                    "incorrect_answers": ["3", "5", "6"]
                  },
                  {
                    "question": "What is the capital of France?",
                    "correct_answer": "Paris",
                    "incorrect_answers": ["Berlin", "Madrid", "Rome"]
                  }
                ]
                """;
        ObjectMapper mapper = new ObjectMapper();
        JsonNode results = mapper.readTree(json);

        Map<String, String> correctAnswers = new HashMap<>();
        List<Question> questions = TriviaHelper.parseQuestions(results, correctAnswers);

        assertEquals(2, questions.size());
        assertEquals(2, correctAnswers.size());
    }
}
