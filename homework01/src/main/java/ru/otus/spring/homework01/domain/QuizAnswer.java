package ru.otus.spring.homework01.domain;

public class QuizAnswer {
    private int quizId;
    private int answer;

    public QuizAnswer(int quizId, int answer) {
        this.quizId = quizId;
        this.answer = answer;
    }

    public int getQuizId() {
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
