package ru.otus.spring.library.service.impl;

import org.springframework.stereotype.Service;
import ru.otus.spring.library.domain.Author;
import ru.otus.spring.library.domain.Book;
import ru.otus.spring.library.repository.AuthorDao;
import ru.otus.spring.library.repository.JpaRepositoryException;
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
    public Author addAuthor(String name) {
        return authorDao.findByName(name).orElseGet(() -> {
            Author author = new Author(name);
            authorDao.saveAndFlush(author);
            return author;
        });
    }

    @Override
    public Author updateAuthor(long id, String name) throws LibraryServiceException {
        Author author = authorDao.findById(id).orElseThrow(() -> new LibraryServiceException("Can't get author with id: " + id));
        author.setName(name);
        authorDao.saveAndFlush(author);
        return author;
    }

    @Override
    public Author getAuthorById(long id) throws LibraryServiceException {
        return authorDao.findById(id).orElseThrow(() -> new LibraryServiceException("Can't get author with id: " + id));
    }

    @Override
    public Author getAuthorByIdWithBook(long id) throws LibraryServiceException {
        try {
            return authorDao.getAuthorByIdWithBook(id);
        } catch (JpaRepositoryException e) {
            ms.printMessage(e.getMessage());
            throw new LibraryServiceException("Can't get comment with id: " + id);
        }
    }

    @Override
    public void deleteAuthorById(long id) throws LibraryServiceException {
        List<Book> allBooksWhereAuthorId;
        try {
            allBooksWhereAuthorId = authorDao.getAuthorByIdWithBook(id).getBooks();
        } catch (JpaRepositoryException e) {
            throw new LibraryServiceException("Can't delete author with id: " + id);
        }
        if (allBooksWhereAuthorId.size() > 0) {
            throw new LibraryServiceException("Can't delete author with id: " + id + ". Author still has books!");
        }
        authorDao.deleteById(id);
    }

    @Override
    public List<Author> getAllAuthors() {
        return authorDao.findAll();
    }
}
