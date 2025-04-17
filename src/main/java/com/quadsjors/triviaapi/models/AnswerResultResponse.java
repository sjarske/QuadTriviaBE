package com.quadsjors.triviaapi.models;

import java.util.Map;

public class AnswerResultResponse {
    private int total;
    private int correct;
    private Map<String, Boolean> resultPerQuestion;

    public AnswerResultResponse() {}

    public AnswerResultResponse(int total, int correct, Map<String, Boolean> resultPerQuestion) {
        this.total = total;
        this.correct = correct;
        this.resultPerQuestion = resultPerQuestion;
    }

    public int getTotal() {
        return total;
    }

    public int getCorrect() {
        return correct;
    }

    public Map<String, Boolean> getResultPerQuestion() {
        return resultPerQuestion;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public void setCorrect(int correct) {
        this.correct = correct;
    }

    public void setResultPerQuestion(Map<String, Boolean> resultPerQuestion) {
        this.resultPerQuestion = resultPerQuestion;
    }
}
