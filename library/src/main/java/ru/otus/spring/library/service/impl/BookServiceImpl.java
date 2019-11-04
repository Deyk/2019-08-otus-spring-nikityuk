package ru.otus.spring.library.service.impl;

import org.springframework.stereotype.Service;
import ru.otus.spring.library.domain.Author;
import ru.otus.spring.library.domain.Book;
import ru.otus.spring.library.repository.BookDao;
import ru.otus.spring.library.repository.JpaRepositoryException;
import ru.otus.spring.library.service.AuthorService;
import ru.otus.spring.library.service.BookService;
import ru.otus.spring.library.service.LibraryServiceException;
import ru.otus.spring.library.service.MessageService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {
    private final BookDao bookDao;
    private final AuthorService authorService;
    private final MessageService ms;

    public BookServiceImpl(BookDao bookDao, AuthorService authorService, MessageService ms) {
        this.bookDao = bookDao;
        this.authorService = authorService;
        this.ms = ms;
    }

    @Override
    public Book updateBook(String title, String authorName) {
        Optional<Book> bookOptional = bookDao.getBookByTitle(title);
        if (bookOptional.isPresent()) {
            return bookDao.updateBook(bookOptional.get());
        } else {
            Author author = authorService.updateAuthor(authorName);
            return bookDao.updateBook(new Book(0, title, Collections.singletonList(author)));
        }
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
