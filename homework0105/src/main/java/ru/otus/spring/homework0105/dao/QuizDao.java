package ru.otus.spring.homework0105.dao;

import ru.otus.spring.homework0105.domain.QuizUnit;

import java.util.List;

public interface QuizDao {

    List<QuizUnit> readQuiz() throws ReadQuizException;
}
