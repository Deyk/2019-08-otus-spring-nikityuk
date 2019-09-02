package ru.otus.spring.homework01.dao.impl;

import ru.otus.spring.homework01.dao.QuizDao;
import ru.otus.spring.homework01.domain.QuizResult;
import ru.otus.spring.homework01.domain.QuizUnit;
import ru.otus.spring.homework01.domain.User;
import ru.otus.spring.homework01.util.Result;

import java.util.List;

import static java.lang.System.out;

//TODO: implement after DB will be added
public class QuizDaoImpl implements QuizDao {
    @Override
    public List<QuizResult> findQuizListByUser(User user) {
        out.println("Get quiz list by user: " + user);
        return null;
    }

    @Override
    public QuizResult findQuizById(int quizId) {
        out.println("Get quiz by id: " + quizId);
        return null;
    }

    @Override
    public void addQuizResult(QuizResult result) {
        out.println("Quiz result saved: \n" + result);
    }
}
