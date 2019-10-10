package ru.otus.spring.homework0105;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import ru.otus.spring.homework0105.service.QuizIoService;

@Configuration
@SpringBootApplication
public class Application {

    public Application(ApplicationContext applicationContext) {
        QuizIoService quizIoService = applicationContext.getBean(QuizIoService.class);
        quizIoService.printUserLoginInfo();
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
}
