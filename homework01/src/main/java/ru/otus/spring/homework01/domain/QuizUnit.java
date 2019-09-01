package ru.otus.spring.homework01.domain;

import java.util.Collections;
import java.util.List;

public class QuizUnit {
    private String question;
    private List<String> answers;
    private int rightAnswer;

    public QuizUnit(String question, List<String> answers, int rightAnswer) {
        this.question = question;
        this.answers = answers;
        this.rightAnswer = rightAnswer;
    }

    public String getQuestion() {
        return question;
    }

    public List<String> getAnswers() {
        return Collections.unmodifiableList(answers);
    }

    public int getRightAnswer() {
        return rightAnswer;
    }

    @Override
    public String toString() {
        return "\n\tQuiestion: " + this.getQuestion() + "\n" +
                "\tAnswers: " + this.getAnswers().toString() + "\n" +
                "\tRight answer: " + this.getRightAnswer();
    }
}
