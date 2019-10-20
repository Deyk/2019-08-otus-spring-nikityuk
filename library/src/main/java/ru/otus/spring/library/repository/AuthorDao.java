package ru.otus.spring.library.repository;

import ru.otus.spring.library.domain.Author;

import java.util.List;

public interface AuthorDao {
    Author insertAuthor(String name);

    Author updateAuthor(Author author);

    Author getAuthorById(long id);

    void deleteAuthorById(long Id);

    List<Author> getAllAuthors();
}
