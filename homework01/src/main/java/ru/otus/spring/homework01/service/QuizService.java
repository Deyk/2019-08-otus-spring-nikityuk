package ru.otus.spring.homework01.service;

import ru.otus.spring.homework01.domain.QuizResult;
import ru.otus.spring.homework01.domain.QuizUnit;
import ru.otus.spring.homework01.domain.User;
import ru.otus.spring.homework01.util.Result;

import java.util.List;

public interface QuizService {
    List<QuizResult> getQuizListByUser(User user);

    QuizResult getQuizById(int quizId);

    void saveQuizResult(QuizResult result);
}
