package ru.otus.spring.library.service.impl;

import org.springframework.stereotype.Service;
import ru.otus.spring.library.domain.Book;
import ru.otus.spring.library.repository.BookDao;
import ru.otus.spring.library.repository.JdbcRepositoryException;
import ru.otus.spring.library.service.BookService;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    private final BookDao bookDao;

    public BookServiceImpl(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    @Override
    public Book addBook(String title, long authorId) {
        return bookDao.insertBook(title, authorId);
    }

    @Override
    public Book getBookById(long id) {
        try {
            return bookDao.getBookById(id);
        } catch (JdbcRepositoryException e) {
            e.getMessage();
        }
        return null;
    }

    @Override
    public Book deleteBookById(long id) {
        return bookDao.deleteBookById(id);
    }

    @Override
    public List<Book> getAllBooks() {
        return bookDao.getAllBooks();
    }
}
