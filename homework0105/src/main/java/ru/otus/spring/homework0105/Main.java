package ru.otus.spring.homework0105;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ru.otus.spring.homework0105.service.QuizRunner;

@Configuration
@ComponentScan
public class Main {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Main.class);
        QuizRunner quizRunner = context.getBean(QuizRunner.class);
        quizRunner.startQuiz();
    }
}
