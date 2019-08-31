package ru.otus.spring.homework01.domain;

import java.util.Collections;
import java.util.List;

public class QuizUnit {
    private String question;
    private List<String> answers;

    public QuizUnit(String question, List<String> answers) {
        this.question = question;
        this.answers = answers;
    }

    public String getQuestion() {
        return question;
    }

    public List<String> getAnswers() {
        return Collections.unmodifiableList(answers);
    }
}
