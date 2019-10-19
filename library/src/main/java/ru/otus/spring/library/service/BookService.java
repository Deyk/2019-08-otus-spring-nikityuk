package ru.otus.spring.library.service;

import ru.otus.spring.library.domain.Book;

import java.util.List;

public interface BookService {
    Book addBook(String title);

    Book getBookById(long id);

    List<Book> findBooksByAuthor(String name, String surname);

    List<Book> findBooksByGenre(String genreTitle);

    Book deleteBookById(long id);

    List<Book> getAllBooks();
}
