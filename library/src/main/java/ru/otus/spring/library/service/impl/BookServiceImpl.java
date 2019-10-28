package ru.otus.spring.library.service.impl;

import org.springframework.stereotype.Service;
import ru.otus.spring.library.domain.Book;
import ru.otus.spring.library.repository.BookDao;
import ru.otus.spring.library.repository.JdbcRepositoryException;
import ru.otus.spring.library.service.BookService;
import ru.otus.spring.library.service.LibraryServiceException;
import ru.otus.spring.library.service.MessageService;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    private final BookDao bookDao;
    private final MessageService ms;

    public BookServiceImpl(BookDao bookDao, MessageService ms) {
        this.bookDao = bookDao;
        this.ms = ms;
    }

    @Override
    public Book addBook(String title, String authorName) throws LibraryServiceException {
        try {
            return bookDao.insertBook(title, authorName);
        } catch (JdbcRepositoryException e) {
            ms.printMessage(e.getMessage());
            throw new LibraryServiceException("Can't add book with title: " + title + ", author: " + authorName);
        }
    }

    @Override
    public int updateBook(long id, String title) {
        return bookDao.updateBook(id, title);
    }

    @Override
    public Book getBookById(long id) throws LibraryServiceException {
        try {
            return bookDao.getBookById(id);
        } catch (JdbcRepositoryException e) {
            ms.printMessage(e.getMessage());
            throw new LibraryServiceException("Can't get book with id: " + id);
        }
    }

    @Override
    public int deleteBookById(long id) {
        return bookDao.deleteBookById(id);
    }

    @Override
    public List<Book> getAllBooks() {
        return bookDao.getAllBooks();
    }
}
