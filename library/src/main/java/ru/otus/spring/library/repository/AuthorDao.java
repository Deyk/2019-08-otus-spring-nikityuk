package ru.otus.spring.library.repository;

import ru.otus.spring.library.domain.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorDao {

    Author saveAuthor(Author author);

    Author getAuthorById(long id) throws JpaRepositoryException;

    Optional<Author> getAuthorByName(String name);

    void deleteAuthorById(long Id) throws JpaRepositoryException;

    List<Author> getAllAuthors();
}
