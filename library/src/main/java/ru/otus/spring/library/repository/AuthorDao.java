package ru.otus.spring.library.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;
import ru.otus.spring.library.domain.Author;

public interface AuthorDao extends ReactiveMongoRepository<Author, String> {
    Mono<Author> findByName(String authorName);
}
