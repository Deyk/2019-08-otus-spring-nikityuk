package ru.otus.spring.library.repository;

import ru.otus.spring.library.domain.Author;

import java.util.Optional;

public interface AuthorDaoCustom {
    Author getAuthorByIdWithBook(long id) throws JpaRepositoryException;

    Optional<Author> findByNameWithBook(String name);
}
