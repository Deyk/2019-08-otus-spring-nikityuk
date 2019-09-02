package ru.otus.spring.homework01.service;

import ru.otus.spring.homework01.domain.QuizSourceFile;
import ru.otus.spring.homework01.domain.QuizUnit;

import java.io.FileNotFoundException;
import java.util.List;

public interface FileReader {

    List<QuizUnit> readQuiz(QuizSourceFile quizSourceFile) throws FileNotFoundException;
}
