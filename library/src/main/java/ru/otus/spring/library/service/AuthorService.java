package ru.otus.spring.library.service;

import ru.otus.spring.library.domain.Author;

import java.util.List;

public interface AuthorService {

    Author addAuthor(String name);

    Author updateAuthor(long id, String name) throws LibraryServiceException;

    Author getAuthorById(long id) throws LibraryServiceException;

    Author getAuthorByIdWithBook(long id) throws LibraryServiceException;

    void deleteAuthorById(long Id) throws LibraryServiceException;

    List<Author> getAllAuthors();
}
