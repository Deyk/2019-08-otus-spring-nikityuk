package ru.otus.spring.homework01.dao;

import ru.otus.spring.homework01.domain.QuizResult;
import ru.otus.spring.homework01.domain.QuizUnit;
import ru.otus.spring.homework01.domain.User;
import ru.otus.spring.homework01.util.Result;

public interface QuizDao {
    QuizResult findQuizByUser(User user);

    void addQuizResult(User user, QuizUnit quizUnit, Result result);
}
