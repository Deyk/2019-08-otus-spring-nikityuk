package ru.otus.spring.library.service;

import ru.otus.spring.library.domain.Author;

import java.util.List;

public interface AuthorService {

    Author addAuthor(String name);

    Author updateAuthor(String id, String name) throws LibraryServiceException;

    Author getAuthorById(String id) throws LibraryServiceException;

    Author getAuthorByIdWithBook(String id) throws LibraryServiceException;

    void deleteAuthorById(String Id) throws LibraryServiceException;

    List<Author> getAllAuthors();
}
