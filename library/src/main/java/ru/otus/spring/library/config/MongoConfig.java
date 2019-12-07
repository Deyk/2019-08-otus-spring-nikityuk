package ru.otus.spring.library.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import ru.otus.spring.library.event.AuthorCascadeMongoEventListener;
import ru.otus.spring.library.event.BookCascadeMongoEventListener;

@Configuration
@EnableMongoRepositories(basePackages = "ru.otus.spring.library.repository")
public class MongoConfig {
    @Bean
    public BookCascadeMongoEventListener bookCascadingMongoEventListener() {
        return new BookCascadeMongoEventListener();
    }

    @Bean
    public AuthorCascadeMongoEventListener authorCascadingMongoEventListener() {
        return new AuthorCascadeMongoEventListener();
    }
}
