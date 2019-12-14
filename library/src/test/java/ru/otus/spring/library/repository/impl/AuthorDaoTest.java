package ru.otus.spring.library.repository.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.spring.library.domain.Author;
import ru.otus.spring.library.repository.AuthorDao;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Тесты репозитория для работы с авторами")
@DataMongoTest
@ComponentScan({"ru.otus.spring.library.event", "ru.otus.spring.library.service"})
class AuthorDaoTest {
    private static final String NEW_AUTHOR_NAME = "new author";
    private static final String AUTHOR_ID = "1";
    private static final String AUTHOR_ID_2 = "2";
    private static final String NOT_EXISTING_AUTHOR_ID = "0";

    @Autowired
    AuthorDao authorDao;
    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    @DisplayName("Должен добавлять автора")
    void insertAuthor() {
        Author author = new Author(AUTHOR_ID, NEW_AUTHOR_NAME);
        authorDao.save(author);
        Author expectedAuthor = mongoTemplate.findById(AUTHOR_ID, Author.class);
        assertThat(author).isEqualToComparingFieldByFieldRecursively(expectedAuthor);
    }

    @Test
    @DisplayName("Должен изменять поля автора")
    void saveAuthor() {
        Author author = new Author(AUTHOR_ID, NEW_AUTHOR_NAME);
        authorDao.save(author);
        Author expectedAuthor = mongoTemplate.findById(AUTHOR_ID, Author.class);
        assertThat(author).isEqualToComparingFieldByFieldRecursively(expectedAuthor);
    }

    @Test
    @DisplayName("Должен получать существующего автора по id")
    void getAuthorById() {
        Optional<Author> author = authorDao.findById(AUTHOR_ID);
        author.ifPresent(value -> {
            Author expectedAuthor = mongoTemplate.findById(AUTHOR_ID, Author.class);
            assertThat(value).isEqualToComparingFieldByFieldRecursively(expectedAuthor);
        });
    }

    @Test
    @DisplayName("Должен получать существующего автора по имени")
    void getAuthorByName() {
        Optional<Author> author = authorDao.findByName(NEW_AUTHOR_NAME);
        author.ifPresent(value -> {
            Author expectedAuthor = mongoTemplate.findById(AUTHOR_ID, Author.class);
            assertThat(value).isEqualToComparingFieldByFieldRecursively(expectedAuthor);
        });
    }

    @Test
    @DisplayName("Должен возвращать Optional.empty при попытке достать несуществующего автора")
    void getNonexistentAuthorById() {
        assertEquals(authorDao.findById(NOT_EXISTING_AUTHOR_ID), Optional.empty());
    }

    @Test
    @DisplayName("Должен удалять все записи автора")
    void deleteAuthorWithBookById() {
        mongoTemplate.save(new Author(AUTHOR_ID_2, NEW_AUTHOR_NAME));
        authorDao.deleteById(AUTHOR_ID_2);
        assertEquals(authorDao.findById(AUTHOR_ID_2), Optional.empty());
    }

    @Test
    @DisplayName("Должен вернуть все записи авторов")
    void getAllAuthors() {
        mongoTemplate.save(new Author(AUTHOR_ID_2, NEW_AUTHOR_NAME));
        assertThat(authorDao.findAll()).hasSize(2);
    }
}