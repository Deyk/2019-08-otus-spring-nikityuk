package ru.otus.spring.homework01.dao.impl;

import ru.otus.spring.homework01.dao.QuizDao;
import ru.otus.spring.homework01.domain.QuizResult;
import ru.otus.spring.homework01.domain.QuizUnit;
import ru.otus.spring.homework01.domain.User;
import ru.otus.spring.homework01.util.Result;

import static java.lang.System.out;

//TODO: implement after DB will be added
public class QuizDaoImpl implements QuizDao {
    @Override
    public QuizResult findQuizByUser(User user) {
        out.println("User added:" + user);
        return null;
    }

    @Override
    public void addQuizResult(User user, QuizUnit quizUnit, Result result) {
        out.println("Quiz saved: " + user + ", " + quizUnit + ", " + result);
    }
}
