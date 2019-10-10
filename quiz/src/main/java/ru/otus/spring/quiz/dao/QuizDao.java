package ru.otus.spring.quiz.dao;

import ru.otus.spring.quiz.domain.QuizUnit;

import java.util.List;

public interface QuizDao {

    List<QuizUnit> readQuiz() throws ReadQuizException;
}
