package ru.otus.spring.library.service;

import ru.otus.spring.library.domain.Book;

import java.util.List;

public interface BookService {
    Book addBook(String title, String authorName);

    Book updateBook(long id, String title, String authorName) throws LibraryServiceException;

    Book getBookById(long id) throws LibraryServiceException;

    void deleteBookById(long id) throws LibraryServiceException;

    List<Book> getAllBooks();
}
