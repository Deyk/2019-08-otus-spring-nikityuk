package ru.otus.spring.library.service.impl;

import org.springframework.stereotype.Service;
import ru.otus.spring.library.domain.Author;
import ru.otus.spring.library.domain.Book;
import ru.otus.spring.library.repository.BookDao;
import ru.otus.spring.library.repository.CommentDao;
import ru.otus.spring.library.service.BookService;
import ru.otus.spring.library.service.LibraryServiceException;
import ru.otus.spring.library.service.MessageService;

import java.util.Collections;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    private final BookDao bookDao;
    private final CommentDao commentDao;
    private final MessageService ms;

    public BookServiceImpl(BookDao bookDao, CommentDao commentDao, MessageService ms) {
        this.bookDao = bookDao;
        this.commentDao = commentDao;
        this.ms = ms;
    }

    @Override
    public Book addBook(String title, String authorName) {
        Book book = new Book(0L, title, Collections.singletonList(new Author(0L, authorName)));
        bookDao.saveAndFlush(book);
        return book;
    }

    @Override
    public Book updateBook(long id, String title, String authorName) throws LibraryServiceException {
        Book book = bookDao.findById(id).orElseThrow(() -> new LibraryServiceException("Can't get book with id: " + id));
        List<Author> authors = book.getAuthors();
        if (authors.stream().noneMatch(author -> authorName.equalsIgnoreCase(author.getName()))) {
            authors.add(new Author(0L, authorName));
            book.setAuthors(authors);
        }
        book.setTitle(title);
        bookDao.saveAndFlush(book);
        return book;
    }

    @Override
    public Book getBookById(long id) throws LibraryServiceException {
        return bookDao.findById(id).orElseThrow(() -> new LibraryServiceException("Can't get book with id: " + id));
    }

    @Override
    public void deleteBookById(long id) {
        if (bookDao.existsById(id)) {
            commentDao.deleteAllByBook_Id(id);
            bookDao.deleteById(id);
        }
    }

    @Override
    public List<Book> getAllBooks() {
        return bookDao.findAll();
    }
}
