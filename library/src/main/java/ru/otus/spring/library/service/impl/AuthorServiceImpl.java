package ru.otus.spring.library.service.impl;

import org.springframework.stereotype.Service;
import ru.otus.spring.library.domain.Author;
import ru.otus.spring.library.repository.AuthorDao;
import ru.otus.spring.library.repository.JdbcRepositoryException;
import ru.otus.spring.library.service.AuthorService;
import ru.otus.spring.library.service.LibraryServiceException;
import ru.otus.spring.library.service.MessageService;

import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorDao authorDao;
    private final MessageService ms;

    public AuthorServiceImpl(AuthorDao authorDao, MessageService ms) {
        this.authorDao = authorDao;
        this.ms = ms;
    }

    @Override
    public Author insertAuthor(String name) {
        return authorDao.insertAuthor(name);
    }

    @Override
    public int updateAuthor(long id, String name) throws LibraryServiceException {
        Author author = new Author(id, name);
        try {
            return authorDao.updateAuthor(author);
        } catch (JdbcRepositoryException e) {
            ms.printMessage(e.getMessage());
            throw new LibraryServiceException("Can't update author: " + author);
        }
    }

    @Override
    public Author getAuthorById(long id) throws LibraryServiceException {
        try {
            return authorDao.getAuthorById(id);
        } catch (JdbcRepositoryException e) {
            ms.printMessage(e.getMessage());
            throw new LibraryServiceException("Can't get author with id: " + id);
        }
    }

    @Override
    public int deleteAuthorById(long id) throws LibraryServiceException {
        try {
            return authorDao.deleteAuthorById(id);
        } catch (JdbcRepositoryException e) {
            ms.printMessage(e.getMessage());
            throw new LibraryServiceException("Can't delete author with id: " + id);
        }
    }

    @Override
    public List<Author> getAllAuthors() {
        return authorDao.getAllAuthors();
    }
}
