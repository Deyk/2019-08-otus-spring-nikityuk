package ru.otus.spring.homework0105;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.spring.homework0105.service.QuizRunner;

public class Main {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");
        QuizRunner quizRunner = (QuizRunner) context.getBean("quizRunner");

        quizRunner.startQuiz();
    }
}
