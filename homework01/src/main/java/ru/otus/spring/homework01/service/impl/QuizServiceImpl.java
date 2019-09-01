package ru.otus.spring.homework01.service.impl;

import ru.otus.spring.homework01.dao.QuizDao;
import ru.otus.spring.homework01.domain.QuizResult;
import ru.otus.spring.homework01.domain.QuizUnit;
import ru.otus.spring.homework01.domain.User;
import ru.otus.spring.homework01.service.QuizService;
import ru.otus.spring.homework01.util.Result;

public class QuizServiceImpl implements QuizService {
    private QuizDao quizDao;

    public QuizServiceImpl(QuizDao quizDao) {
        this.quizDao = quizDao;
    }

    @Override
    public QuizResult getQuizByUser(User user) {
        return quizDao.findQuizByUser(user);
    }

    @Override
    public void saveQuizResult(User user, QuizUnit quizUnit, Result result) {
        quizDao.addQuizResult(user, quizUnit, result);
    }

    @Override
    public Result countQuizScore(QuizUnit quizUnit) {
        return null;
    }
}
