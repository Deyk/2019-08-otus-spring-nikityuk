package ru.otus.spring.library.service;

import ru.otus.spring.library.domain.Book;

import java.util.List;

public interface BookService {
    Book updateBook(String title, String authorName);

    Book getBookById(long id) throws LibraryServiceException;

    void deleteBookById(long id) throws LibraryServiceException;

    List<Book> getAllBooks();
}
