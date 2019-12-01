package ru.otus.spring.library.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring.library.domain.Author;

import java.util.Optional;

public interface AuthorDao extends MongoRepository<Author, String> {
    Optional<Author> findByName(String authorName);
}
