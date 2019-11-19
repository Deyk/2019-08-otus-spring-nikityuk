package ru.otus.spring.library.service.impl;

import org.springframework.stereotype.Service;
import ru.otus.spring.library.domain.Author;
import ru.otus.spring.library.domain.Book;
import ru.otus.spring.library.repository.AuthorDao;
import ru.otus.spring.library.repository.BookDao;
import ru.otus.spring.library.service.AuthorService;
import ru.otus.spring.library.service.LibraryServiceException;

import java.util.List;
import java.util.Optional;

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
        Optional<Author> authorOptional = authorDao.getAuthorByName(name);
        if (authorOptional.isPresent()) {
            return authorOptional.get();
        } else {
            Author author = new Author(0L, name);
            authorDao.saveAndFlush(author);
            return author;
        }
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
    public void deleteAuthorById(long id) throws LibraryServiceException {
        List<Book> allBooksWhereAuthorId = bookDao.getAllBooksWhereAuthorId(id);
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
