package ru.otus.spring.library.service;

import ru.otus.spring.library.domain.Book;

import java.util.List;

public interface BookService {
    Book addBook(String title, String authorName);

    Book updateBook(String id, String title, String authorName) throws LibraryServiceException;

    Book getBookById(String id) throws LibraryServiceException;

    void deleteBookById(String id);

    List<Book> getAllBooks();
}
