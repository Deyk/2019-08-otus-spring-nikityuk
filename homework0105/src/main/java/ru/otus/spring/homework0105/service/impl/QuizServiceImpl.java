package ru.otus.spring.homework0105.service.impl;

import ru.otus.spring.homework0105.dao.QuizDao;
import ru.otus.spring.homework0105.domain.QuizResult;
import ru.otus.spring.homework0105.service.QuizService;

public class QuizServiceImpl implements QuizService {
    private QuizDao quizDao;

    public QuizServiceImpl(QuizDao quizDao) {
        this.quizDao = quizDao;
    }

    @Override
    public void saveQuizResult(QuizResult result) {
        quizDao.addQuizResult(result);
    }
}
