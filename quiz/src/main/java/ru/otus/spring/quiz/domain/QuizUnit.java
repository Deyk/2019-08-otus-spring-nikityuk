package ru.otus.spring.quiz.domain;

import java.util.Collections;
import java.util.List;

public class QuizUnit {
    private String quizId;
    private String question;
    private List<String> answers;
    private int rightAnswer;

    public QuizUnit(String quizId, String question, List<String> answers, int rightAnswer) {
        this.quizId = quizId;
        this.question = question;
        this.answers = answers;
        this.rightAnswer = rightAnswer;
    }

    public String getQuizId() {
        return quizId;
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
        return "\n\tId: " + this.getQuizId() + "\n" +
                "\tQuestion: " + this.getQuestion() + "\n" +
                "\tAnswers: " + this.getAnswers().toString() + "\n" +
                "\tRight answer: " + this.getRightAnswer() + "\n";
    }
}
