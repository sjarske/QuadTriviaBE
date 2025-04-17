package com.quadsjors.triviaapi.models;

import java.util.List;

public class Question {
    private String id;
    private String question;
    private List<String> options;

    public Question() {}

    public Question(String id, String question, List<String> options) {
        this.id = id;
        this.question = question;
        this.options = options;
    }

    public String getId() {
        return id;
    }

    public String getQuestion() {
        return question;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }
}
