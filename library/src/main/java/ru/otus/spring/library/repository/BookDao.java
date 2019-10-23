package ru.otus.spring.library.repository;

import ru.otus.spring.library.domain.Book;

import java.util.List;

public interface BookDao {
    Book insertBook(String title, long authorId);

    int updateBook(long id, String title, long authorId) throws JdbcRepositoryException;

    Book getBookById(long id) throws JdbcRepositoryException;

    int deleteBookById(long id);

    List<Book> getAllBooks();
}
