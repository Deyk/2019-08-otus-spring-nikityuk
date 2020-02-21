package ru.otus.spring.library.service.impl;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.spring.library.domain.Author;
import ru.otus.spring.library.repository.AuthorDao;
import ru.otus.spring.library.repository.BookDao;
import ru.otus.spring.library.service.AuthorService;
import ru.otus.spring.library.service.LibraryServiceException;

@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorDao authorDao;
    private final BookDao bookDao;

    public AuthorServiceImpl(AuthorDao authorDao, BookDao bookDao) {
        this.authorDao = authorDao;
        this.bookDao = bookDao;
    }

    @Override
    public Mono<Author> addAuthor(String name) {
        return authorDao.findByName(name).onErrorResume(fallback -> authorDao.save(new Author(name)));
    }

    @Override
    public Mono<Author> updateAuthor(String id, String name) {
        return authorDao.findById(id)
                .map(author -> new Author(author.getId(), name))
                .flatMap(authorDao::save)
                .onErrorContinue((throwable, o) -> throwable.addSuppressed(new LibraryServiceException("Can't get author with id: " + id)));
    }

    @Override
    public Mono<Void> deleteAuthorById(String id) throws LibraryServiceException {
        long countByAuthorId = bookDao.countByAuthorId(id);
        if (countByAuthorId > 0) {
            throw new LibraryServiceException("Can't delete author with id: " + id + ". Author still has " + countByAuthorId + " books!");
        }
        return authorDao.deleteById(id);
    }

    @Override
    public Flux<Author> getAllAuthors() {
        return authorDao.findAll();
    }
}
