package ru.otus.spring.library.service.impl;

import org.springframework.stereotype.Service;
import ru.otus.spring.library.domain.Author;
import ru.otus.spring.library.repository.AuthorDao;
import ru.otus.spring.library.repository.BookDao;
import ru.otus.spring.library.service.AuthorService;
import ru.otus.spring.library.service.LibraryServiceException;

import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorDao authorDao;
    private final BookDao bookDao;

    public AuthorServiceImpl(AuthorDao authorDao, BookDao bookDao) {
        this.authorDao = authorDao;
        this.bookDao = bookDao;
    }

    @Override
    public Author addAuthor(String name) {
        return authorDao.findByName(name).orElseGet(() -> {
            Author author = new Author(name);
            authorDao.save(author);
            return author;
        });
    }

    @Override
    public Author updateAuthor(String id, String name) throws LibraryServiceException {
        Author author = authorDao.findById(id).orElseThrow(() -> new LibraryServiceException("Can't get author with id: " + id));
        author.setName(name);
        authorDao.save(author);
        return author;
    }

    @Override
    public Author getAuthorById(String id) throws LibraryServiceException {
        return authorDao.findById(id).orElseThrow(() -> new LibraryServiceException("Can't get author with id: " + id));
    }

    @Override
    public void deleteAuthorById(String id) throws LibraryServiceException {
        long countByAuthorId = bookDao.countByAuthorId(id);
        if (countByAuthorId > 0) {
            throw new LibraryServiceException("Can't delete author with id: " + id + ". Author still has " + countByAuthorId + " books!");
        }
        authorDao.deleteById(id);
    }

    @Override
    public List<Author> getAllAuthors() {
        return authorDao.findAll();
    }
}
