package ru.otus.spring.library.repository;

import ru.otus.spring.library.domain.Book;

import java.util.List;

public interface BookDao {
    Book insertBook(String title, long authorId);

    Book updateBook(Book book);

    Book getBookById(long id) throws JdbcRepositoryException;

    int deleteBookById(long id);

    List<Book> getAllBooks();
}
