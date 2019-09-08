package ru.otus.spring.homework0105.dao;

import ru.otus.spring.homework0105.domain.QuizResult;
import ru.otus.spring.homework0105.domain.QuizUnit;

import java.util.List;

public interface QuizDao {
    void saveQuizResult(QuizResult result);

    List<QuizUnit> readQuizFromFile() throws ReadQuizException;
}
