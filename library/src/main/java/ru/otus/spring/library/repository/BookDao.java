package ru.otus.spring.library.repository;

import ru.otus.spring.library.domain.Book;

import java.util.List;

public interface BookDao {
    Book insertBook(String title, String authorName);

    int updateBook(long id, String title);

    Book getBookById(long id) throws JdbcRepositoryException;

    int deleteBookById(long id);

    List<Book> getAllBooks();
}
