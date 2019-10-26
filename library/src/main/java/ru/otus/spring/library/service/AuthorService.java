package ru.otus.spring.library.service;

import ru.otus.spring.library.domain.Author;

import java.util.List;

public interface AuthorService {
    Author insertAuthor(String name) throws LibraryServiceException;

    int updateAuthor(long id, String name) throws LibraryServiceException;

    Author getAuthorById(long id) throws LibraryServiceException;

    int deleteAuthorById(long Id) throws LibraryServiceException;

    List<Author> getAllAuthors();

    List<Author> getAllUniqueAuthors();
}
