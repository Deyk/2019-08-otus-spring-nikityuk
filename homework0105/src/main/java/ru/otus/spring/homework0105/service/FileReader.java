package ru.otus.spring.homework0105.service;

import ru.otus.spring.homework0105.domain.QuizUnit;

import java.io.FileNotFoundException;
import java.util.List;

public interface FileReader {

    List<QuizUnit> readQuiz() throws FileNotFoundException;
}
