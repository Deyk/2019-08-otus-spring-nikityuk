package ru.otus.spring.library.repository.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.spring.library.domain.Author;
import ru.otus.spring.library.repository.AuthorDao;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Тесты репозитория для работы с авторами")
@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class AuthorDaoTest {
    private static final String NEW_AUTHOR_NAME = "new author";
    private static final String EXISTING_AUTHOR_NAME = "author_01";
    private static final long NEW_AUTHOR_ID = 4L;
    private static final long EXISTING_AUTHOR_ID = 1L;
    private static final long EXISTING_AUTHOR_ID_2 = 2L;
    private static final long EXISTING_AUTHOR_ID_3 = 3L;
    private static final long DEFAULT_ID = 0L;

    @Autowired
    AuthorDao authorDao;
    @Autowired
    private TestEntityManager tem;

    @Test
    @DisplayName("Должен добавлять автора")
    void insertAuthor() {
        Author author = new Author(DEFAULT_ID, NEW_AUTHOR_NAME);
        authorDao.saveAndFlush(author);
        Author expectedAuthor = tem.find(Author.class, NEW_AUTHOR_ID);
        assertThat(author).isEqualToComparingFieldByFieldRecursively(expectedAuthor);
    }

    @Test
    @DisplayName("Должен изменять поля автора")
    void saveAuthor() {
        Author author = new Author(EXISTING_AUTHOR_ID, NEW_AUTHOR_NAME);
        authorDao.saveAndFlush(author);
        Author expectedAuthor = tem.find(Author.class, EXISTING_AUTHOR_ID);
        assertThat(author).isEqualToComparingFieldByFieldRecursively(expectedAuthor);
    }

    @Test
    @DisplayName("Должен получать существующего автора по id")
    void getAuthorById() {
        Optional<Author> author = authorDao.findById(EXISTING_AUTHOR_ID);
        author.ifPresent(value -> {
            Author expectedAuthor = tem.find(Author.class, EXISTING_AUTHOR_ID);
            assertThat(value).isEqualToComparingFieldByFieldRecursively(expectedAuthor);
        });
    }

    @Test
    @DisplayName("Должен получать существующего автора по имени")
    void getAuthorByName() {
        Optional<Author> author = authorDao.findByName(EXISTING_AUTHOR_NAME);
        author.ifPresent(value -> {
            Author expectedAuthor = tem.find(Author.class, EXISTING_AUTHOR_ID);
            assertThat(value).isEqualToComparingFieldByFieldRecursively(expectedAuthor);
        });
    }

    @Test
    @DisplayName("Должен возвращать Optional.empty при попытке достать несуществующего автора")
    void getNonexistentAuthorById() {
        assertEquals(authorDao.findById(NEW_AUTHOR_ID), Optional.empty());
    }

    @Test
    @DisplayName("Должен не удалять несуществующего автора ")
    void deleteNonexistentAuthorById() {
        assertThatThrownBy(() -> authorDao.deleteById(NEW_AUTHOR_ID)).isInstanceOf(EmptyResultDataAccessException.class);
    }

    @Test
    @DisplayName("Должен удалять все записи автора")
    void deleteAuthorWithBookById() {
        authorDao.deleteById(EXISTING_AUTHOR_ID_2);
        assertEquals(authorDao.findById(EXISTING_AUTHOR_ID_2), Optional.empty());
    }

    @Test
    @DisplayName("Должен удалять все записи автора без книг")
    void deleteAuthorById() {
        authorDao.deleteById(EXISTING_AUTHOR_ID_3);
        assertEquals(authorDao.findById(EXISTING_AUTHOR_ID_3), Optional.empty());
    }

    @Test
    @DisplayName("Должен вернуть все записи авторов")
    void getAllAuthors() {
        assertThat(authorDao.findAll()).hasSize(3);
    }
}