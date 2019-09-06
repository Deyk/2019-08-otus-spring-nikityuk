package ru.otus.spring.homework0105.dao;

import ru.otus.spring.homework0105.domain.QuizResult;
import ru.otus.spring.homework0105.domain.User;

import java.util.List;

public interface QuizDao {
    List<QuizResult> findQuizListByUser(User user);

    QuizResult findQuizById(int quizId);

    void addQuizResult(QuizResult result);
}
