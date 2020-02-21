package ru.otus.spring.library.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.spring.library.domain.Author;

public interface AuthorService {

    Mono<Author> addAuthor(String name);

    Mono<Author> updateAuthor(String id, String name);

    Mono<Void> deleteAuthorById(String Id);

    Flux<Author> getAllAuthors();
}
