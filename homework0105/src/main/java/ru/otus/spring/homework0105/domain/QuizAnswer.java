package ru.otus.spring.homework0105.domain;

public class QuizAnswer {
    private String quizId;
    private int answer;

    public QuizAnswer(String quizId, int answer) {
        this.quizId = quizId;
        this.answer = answer;
    }

    public String getQuizId() {
        return quizId;
    }

    public int getAnswer() {
        return answer;
    }

    @Override
    public String toString() {
        return "QuizId: " + quizId + " Answer: " + answer + "\n";
    }
}
