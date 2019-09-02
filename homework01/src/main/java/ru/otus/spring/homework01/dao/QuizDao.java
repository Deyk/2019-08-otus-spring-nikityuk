package ru.otus.spring.homework01.dao;

import ru.otus.spring.homework01.domain.QuizResult;
import ru.otus.spring.homework01.domain.QuizUnit;
import ru.otus.spring.homework01.domain.User;
import ru.otus.spring.homework01.util.Result;

import java.util.List;

public interface QuizDao {
    List<QuizResult> findQuizListByUser(User user);

    QuizResult findQuizById(int quizId);

    void addQuizResult(QuizResult result);
}
