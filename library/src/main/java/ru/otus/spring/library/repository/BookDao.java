package ru.otus.spring.library.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import ru.otus.spring.library.domain.Book;

import java.util.List;

public interface BookDao extends MongoRepository<Book, String> {
    @Query(value = "{'authors.id' : ?0}", fields = "{'authors' : 1}")
    List<Book> findByAuthorId_OnlyAuthors(String authorId);

    @Query(value = "{'authors.id' : ?0}", fields = "{'authors' : 1}", count = true)
    long countByAuthorId(String authorId);
}
