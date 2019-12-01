package ru.otus.spring.library.service.impl;

import org.springframework.stereotype.Service;
import ru.otus.spring.library.domain.Author;
import ru.otus.spring.library.domain.Book;
import ru.otus.spring.library.repository.AuthorDao;
import ru.otus.spring.library.service.AuthorService;
import ru.otus.spring.library.service.LibraryServiceException;

import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorDao authorDao;

    public AuthorServiceImpl(AuthorDao authorDao) {
        this.authorDao = authorDao;
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
    public Author getAuthorByIdWithBook(String id) throws LibraryServiceException {
        return authorDao.findById(id).orElseThrow(() -> new LibraryServiceException("Can't get comment with id: " + id));
    }

    @Override
    public void deleteAuthorById(String id) throws LibraryServiceException {
        List<Book> allBooksWhereAuthorId;
        allBooksWhereAuthorId = authorDao.findById(id).orElseThrow(() -> new LibraryServiceException("Can't delete author with id: " + id)).getBooks();
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
