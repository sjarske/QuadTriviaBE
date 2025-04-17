package com.quadsjors.triviaapi.models;

import java.util.Map;

public class AnswerCheckRequest {
    private String sessionId;
    private Map<String, String> answers;

    public AnswerCheckRequest() {}

    public String getSessionId() {
        return sessionId;
    }

    public Map<String, String> getAnswers() {
        return answers;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public void setAnswers(Map<String, String> answers) {
        this.answers = answers;
    }
}
