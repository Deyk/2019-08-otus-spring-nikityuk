package ru.otus.spring.library.repository.impl;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.spring.library.domain.Author;
import ru.otus.spring.library.domain.Book;
import ru.otus.spring.library.repository.AuthorDao;
import ru.otus.spring.library.repository.BookDao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("Тесты репозитория для работы с книгами")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@DataMongoTest
@ComponentScan({"ru.otus.spring.library.event", "ru.otus.spring.library.service"})
class BookDaoTest {
    private static final String AUTHOR_NAME = "author_01";
    private static final String AUTHOR_NAME_2 = "author_02";
    private static final String BOOK_TITLE = "new book";
    private static final String BOOK_TITLE_2 = "new book 2";
    private static final String AUTHOR_ID = "1";
    private static final String AUTHOR_ID_2 = "2";
    private static final String BOOK_ID = "1";
    private static final String BOOK_ID_2 = "2";
    private static final String DEFAULT_ID = "0";

    @Autowired
    BookDao bookDao;
    @Autowired
    AuthorDao authorDao;
    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    @DisplayName("Должен добавлять книгу и автора, если его еще нет")
    void a() {
        Author author = new Author(AUTHOR_ID, AUTHOR_NAME);
        Book book = new Book(BOOK_ID, BOOK_TITLE, Collections.singletonList(author), new ArrayList<>());
        bookDao.save(book);
        Book expectedBook = mongoTemplate.findById(BOOK_ID, Book.class);
        assertThat(book).isEqualToComparingFieldByFieldRecursively(expectedBook);
    }

    @Test
    @DisplayName("Должен добавлять книгу и связывать с автором, если он есть")
    void b() {
        Book book = new Book(BOOK_ID_2, BOOK_TITLE, Collections.singletonList(mongoTemplate.findById(AUTHOR_ID, Author.class)), new ArrayList<>());
        bookDao.save(book);
        Book expectedBook = mongoTemplate.findById(BOOK_ID_2, Book.class);
        assertThat(book).isEqualToComparingFieldByFieldRecursively(expectedBook);
    }

    @Test
    @DisplayName("Должен изменять поля книги")
    void c() {
        Book book = mongoTemplate.findById(BOOK_ID_2, Book.class);
        assertNotNull(book);
        book.setTitle(BOOK_TITLE_2);
        book.setAuthors(Collections.singletonList(new Author(AUTHOR_ID_2, AUTHOR_NAME_2)));
        bookDao.save(book);
        Book expectedBook = mongoTemplate.findById(BOOK_ID_2, Book.class);
        assertThat(book).isEqualToComparingFieldByFieldRecursively(expectedBook);
    }

    @Test
    @DisplayName("Должен получать существующую книгу")
    void d() {
        Optional<Book> book = bookDao.findById(BOOK_ID);
        Book expectedBook = mongoTemplate.findById(BOOK_ID, Book.class);
        book.ifPresent(value -> assertThat(value).isEqualToComparingFieldByFieldRecursively(expectedBook));
    }


    @Test
    @DisplayName("Должен возвращать Optional.empty при попытке достать несуществующую книгу")
    void e() {
        assertEquals(bookDao.findById(DEFAULT_ID), Optional.empty());
    }

    @Test
    @DisplayName("Должен удалять существующую книгу")
    void f() {
        bookDao.deleteById(BOOK_ID);
        assertEquals(bookDao.findById(BOOK_ID), Optional.empty());
        assertEquals(authorDao.findAll().size(), 2);
    }

    @Test
    @DisplayName("Должен получать все книги")
    void g() {
        assertThat(bookDao.findAll()).hasSize(1);
    }
}