package ru.otus.spring.homework01.domain;

import ru.otus.spring.homework01.util.Result;

public class QuizResult {
    private User user;
    private QuizUnit quizUnit;
    private Result result;

    public QuizResult(User user, QuizUnit quizUnit, Result result) {
        this.user = user;
        this.quizUnit = quizUnit;
        this.result = result;
    }

    public User getUser() {
        return user;
    }

    public QuizUnit getQuizUnit() {
        return quizUnit;
    }

    public Result getResult() {
        return result;
    }
}
