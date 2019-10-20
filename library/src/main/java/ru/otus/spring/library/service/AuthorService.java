package ru.otus.spring.library.service;

import ru.otus.spring.library.domain.Author;

import java.util.List;

public interface AuthorService {
    Author updateAuthor(Author author);

    Author getAuthorById(long id);

    Author deleteAuthorById(long Id);

    List<Author> getAllAuthors();
}
