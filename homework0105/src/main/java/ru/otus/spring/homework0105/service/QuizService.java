package ru.otus.spring.homework0105.service;

import ru.otus.spring.homework0105.domain.QuizResult;
import ru.otus.spring.homework0105.domain.User;

import java.util.List;

public interface QuizService {
    List<QuizResult> getQuizListByUser(User user);

    QuizResult getQuizById(int quizId);

    void saveQuizResult(QuizResult result);
}
