package ru.otus.spring.library.repository;

import ru.otus.spring.library.domain.Author;

import java.util.List;

public interface AuthorDao {
    Author insertAuthor(String name);

    int updateAuthor(Author author) throws JdbcRepositoryException;

    Author getAuthorById(long id) throws JdbcRepositoryException;

    int deleteAuthorById(long Id) throws JdbcRepositoryException;

    List<Author> getAllAuthors();
}
