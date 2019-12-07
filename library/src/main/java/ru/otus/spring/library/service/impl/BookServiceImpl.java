package ru.otus.spring.library.service.impl;

import org.springframework.stereotype.Service;
import ru.otus.spring.library.domain.Author;
import ru.otus.spring.library.domain.Book;
import ru.otus.spring.library.repository.BookDao;
import ru.otus.spring.library.service.BookService;
import ru.otus.spring.library.service.LibraryServiceException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    private final BookDao bookDao;

    public BookServiceImpl(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    @Override
    public Book addBook(String title, String authorName) {
        Book book = new Book(title, Collections.singletonList(new Author(authorName)), new ArrayList<>());
        bookDao.save(book);
        return book;
    }

    @Override
    public Book updateBook(String id, String title, String authorName) throws LibraryServiceException {
        Book book = bookDao.findById(id).orElseThrow(() -> new LibraryServiceException("Can't get book with id: " + id));
        List<Author> authors = book.getAuthors();
        if (authors.stream().noneMatch(author -> authorName.equalsIgnoreCase(author.getName()))) {
            Author author = new Author(authorName);
            authors.add(author);
            book.setAuthors(authors);
        }
        book.setTitle(title);
        bookDao.save(book);
        return book;
    }

    @Override
    public Book getBookById(String id) throws LibraryServiceException {
        return bookDao.findById(id).orElseThrow(() -> new LibraryServiceException("Can't get book with id: " + id));
    }

    @Override
    public void deleteBookById(String id) {
        if (bookDao.existsById(id)) {
            bookDao.deleteById(id);
        }
    }

    @Override
    public List<Book> getAllBooks() {
        return bookDao.findAll();
    }
}
