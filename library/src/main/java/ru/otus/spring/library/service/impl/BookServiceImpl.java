package ru.otus.spring.library.service.impl;

import org.springframework.stereotype.Service;
import ru.otus.spring.library.domain.Author;
import ru.otus.spring.library.domain.Book;
import ru.otus.spring.library.repository.BookDao;
import ru.otus.spring.library.repository.JpaRepositoryException;
import ru.otus.spring.library.service.BookService;
import ru.otus.spring.library.service.LibraryServiceException;
import ru.otus.spring.library.service.MessageService;

import java.util.Collections;
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
    public Book addBook(String title, String authorName) {
        return bookDao.saveBook(new Book(0L, title, Collections.singletonList(new Author(0L, authorName))));
    }

    @Override
    public Book updateBook(long id, String title, String authorName) throws LibraryServiceException {
        Book book;
        try {
            book = bookDao.getBookById(id);
        } catch (JpaRepositoryException e) {
            ms.printMessage(e.getMessage());
            throw new LibraryServiceException("Can't get book with id: " + id);
        }
        List<Author> authors = book.getAuthors();
        if (authors.stream().noneMatch(author -> authorName.equalsIgnoreCase(author.getName()))) {
            authors.add(new Author(0L, authorName));
            book.setAuthors(authors);
        }
        book.setTitle(title);
        return bookDao.saveBook(book);
    }

    @Override
    public Book getBookById(long id) throws LibraryServiceException {
        try {
            return bookDao.getBookById(id);
        } catch (JpaRepositoryException e) {
            ms.printMessage(e.getMessage());
            throw new LibraryServiceException("Can't get book with id: " + id);
        }
    }

    @Override
    public void deleteBookById(long id) throws LibraryServiceException {
        try {
            bookDao.deleteBookById(id);
        } catch (JpaRepositoryException e) {
            ms.printMessage(e.getMessage());
            throw new LibraryServiceException("Can't get book with id: " + id);
        }
    }

    @Override
    public List<Book> getAllBooks() {
        return bookDao.getAllBooks();
    }
}
