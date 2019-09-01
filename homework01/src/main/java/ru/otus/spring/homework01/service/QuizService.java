package ru.otus.spring.homework01.service;

import ru.otus.spring.homework01.domain.QuizResult;
import ru.otus.spring.homework01.domain.QuizUnit;
import ru.otus.spring.homework01.domain.User;
import ru.otus.spring.homework01.util.Result;

public interface QuizService {
    QuizResult getQuizByUser(User user);

    void saveQuizResult(User user, QuizUnit quizUnit, Result result);

    Result countQuizScore(QuizUnit quizUnit);
}
