package ru.otus.spring.homework0105.domain;

import ru.otus.spring.homework0105.util.Result;

import java.util.Collections;
import java.util.List;

public class QuizResult {
    private String quizResultId;
    private User user;
    private List<QuizAnswer> quizAnswerList;
    private Result result;

    public QuizResult(String quizResultId, User user, List<QuizAnswer> quizAnswerList, Result result) {
        this.quizResultId = quizResultId;
        this.user = user;
        this.quizAnswerList = quizAnswerList;
        this.result = result;
    }

    public String getQuizResultId() {
        return quizResultId;
    }

    public User getUser() {
        return user;
    }

    public List<QuizAnswer> getQuizAnswerList() {
        return Collections.unmodifiableList(quizAnswerList);
    }

    public Result getResult() {
        return result;
    }

    @Override
    public String toString() {
        return "id: " + quizResultId + "\n" + user + quizAnswerList + "\n" + result + "\n";
    }
}
