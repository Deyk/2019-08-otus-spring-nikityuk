package ru.otus.spring.library.service.impl;

import org.springframework.stereotype.Service;
import ru.otus.spring.library.domain.Author;
import ru.otus.spring.library.domain.Book;
import ru.otus.spring.library.repository.AuthorDao;
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
    private final AuthorDao authorDao;
    private final MessageService ms;

    public BookServiceImpl(BookDao bookDao, AuthorService authorService, AuthorDao authorDao, MessageService ms) {
        this.bookDao = bookDao;
        this.authorService = authorService;
        this.authorDao = authorDao;
        this.ms = ms;
    }

    @Override
    public Book addBook(String title, String authorName) {
        //TODO не работет добавлнение существующего автора
        Optional<Author> authorOptional = authorDao.getAuthorByName(authorName);
        return bookDao.saveBook(new Book(0, title, Collections.singletonList(authorOptional.orElseGet(() -> new Author(0, authorName)))));
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
            authors.add(authorService.addAuthor(authorName));
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
