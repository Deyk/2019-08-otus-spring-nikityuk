package ru.otus.spring.library.service;

import ru.otus.spring.library.domain.Book;

import java.util.List;

public interface BookService {
    Book addBook(String title, long authorId);

    Book updateBook(long id, String title, long authorId);

    Book getBookById(long id) throws LibraryServiceException;

    int deleteBookById(long id);

    List<Book> getAllBooks();
}
