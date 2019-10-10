package ru.otus.spring.quiz.service;

import ru.otus.spring.quiz.domain.QuizAnswer;
import ru.otus.spring.quiz.domain.QuizUnit;
import ru.otus.spring.quiz.domain.User;

import java.util.List;

public interface QuizIoService {

    void printUserLoginInfo();

    void printWelcome();

    void printGeneralInfo(int quizSize, User user);

    void printUserAnswer(String userAnswer);

    void printQuizUnit(QuizUnit quizUnit, List<String> answers);

    void printRightAnswerInfo();

    void printWrongAnswerInfo(String rightAnswer);

    void printQuizResult(List<QuizAnswer> quizAnswerList, User user, int score);

    int getUserAnswer();

    String getLoginError();
}
