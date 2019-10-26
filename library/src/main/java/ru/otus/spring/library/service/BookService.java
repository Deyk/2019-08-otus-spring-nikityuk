package ru.otus.spring.library.service;

import ru.otus.spring.library.domain.Book;

import java.util.List;

public interface BookService {
    Book addBook(String title, String authorName) throws LibraryServiceException;

    int updateBook(long id, String title);

    Book getBookById(long id) throws LibraryServiceException;

    int deleteBookById(long id);

    List<Book> getAllBooks();
}
