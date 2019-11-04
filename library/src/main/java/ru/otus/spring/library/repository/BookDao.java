package ru.otus.spring.library.repository;

import ru.otus.spring.library.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookDao {
    Book updateBook(Book book);

    Book getBookById(long id) throws JpaRepositoryException;

    Optional<Book> getBookByTitle(String title);

    void deleteBookById(long id) throws JpaRepositoryException;

    List<Book> getAllBooks();
}
