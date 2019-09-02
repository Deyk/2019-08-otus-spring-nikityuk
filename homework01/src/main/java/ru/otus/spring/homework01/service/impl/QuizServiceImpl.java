package ru.otus.spring.homework01.service.impl;

import ru.otus.spring.homework01.dao.QuizDao;
import ru.otus.spring.homework01.domain.QuizResult;
import ru.otus.spring.homework01.domain.QuizUnit;
import ru.otus.spring.homework01.domain.User;
import ru.otus.spring.homework01.service.QuizService;
import ru.otus.spring.homework01.util.Result;

import java.util.List;

public class QuizServiceImpl implements QuizService {
    private QuizDao quizDao;

    public QuizServiceImpl(QuizDao quizDao) {
        this.quizDao = quizDao;
    }

    @Override
    public List<QuizResult> getQuizListByUser(User user) {
        return quizDao.findQuizListByUser(user);
    }

    @Override
    public QuizResult getQuizById(int quizId) {
        return quizDao.findQuizById(quizId);
    }

    @Override
    public void saveQuizResult(QuizResult result) {
        quizDao.addQuizResult(result);
    }
}
