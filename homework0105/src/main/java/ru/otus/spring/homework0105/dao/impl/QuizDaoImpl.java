package ru.otus.spring.homework0105.dao.impl;

import ru.otus.spring.homework0105.dao.QuizDao;
import ru.otus.spring.homework0105.domain.QuizResult;
import ru.otus.spring.homework0105.domain.User;

import java.util.List;

import static java.lang.System.out;

public class QuizDaoImpl implements QuizDao {

    @Override
    public void addQuizResult(QuizResult result) {
        out.println("Quiz result saved: \n" + result);
    }
}
