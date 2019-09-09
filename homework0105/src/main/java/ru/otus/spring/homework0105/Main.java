package ru.otus.spring.homework0105;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.spring.homework0105.service.QuizRunner;
import ru.otus.spring.homework0105.service.impl.QuizRunnerImpl;

public class Main {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");
        QuizRunner quizRunner = context.getBean(QuizRunnerImpl.class);

        quizRunner.startQuiz();
    }
}
