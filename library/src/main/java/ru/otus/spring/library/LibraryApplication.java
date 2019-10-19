package ru.otus.spring.library;

import org.h2.tools.Console;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import ru.otus.spring.library.service.MessageService;

import java.sql.SQLException;


@SpringBootApplication
public class LibraryApplication {

    public LibraryApplication(ApplicationContext context) {
        MessageService messageService = context.getBean(MessageService.class);
        messageService.printMessage("Welcome to library! Please, enter login and password...");
    }

    public static void main(String[] args) throws SQLException {
        SpringApplication.run(LibraryApplication.class, args);
        Console.main(args);
    }

}
