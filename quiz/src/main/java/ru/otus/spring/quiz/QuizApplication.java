package ru.otus.spring.quiz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import ru.otus.spring.quiz.service.QuizIoService;

@Configuration
@SpringBootApplication
public class QuizApplication {

    public QuizApplication(ApplicationContext applicationContext) {
        QuizIoService quizIoService = applicationContext.getBean(QuizIoService.class);
        quizIoService.printUserLoginInfo();
    }

    public static void main(String[] args) {
        SpringApplication.run(QuizApplication.class);
    }
}
