package ru.otus.spring.library.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring.library.domain.Book;

public interface BookDao extends MongoRepository<Book, String> {
}
