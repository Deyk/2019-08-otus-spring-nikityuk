package ru.otus.spring.library.repository;

import ru.otus.spring.library.domain.Book;

import java.util.List;

public interface BookDao {
    Book saveBook(Book book);

    Book getBookById(long id) throws JpaRepositoryException;

    void deleteBookById(long id) throws JpaRepositoryException;

    List<Book> getAllBooks();
}
