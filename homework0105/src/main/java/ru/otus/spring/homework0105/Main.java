package ru.otus.spring.homework0105;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.spring.homework0105.domain.QuizUnit;
import ru.otus.spring.homework0105.service.FileReader;
import ru.otus.spring.homework0105.service.QuizRunner;

import java.io.FileNotFoundException;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");
        QuizRunner quizRunner = (QuizRunner) context.getBean("quizRunner");
        FileReader fileReader = (FileReader) context.getBean("fileReader");

        try {
            List<QuizUnit> quizUnitList = fileReader.readQuiz();
            quizRunner.startQuiz(quizUnitList);
        } catch (FileNotFoundException e) {
            e.getMessage();
        }
    }
}
