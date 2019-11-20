package ru.otus.spring.library.repository.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.spring.library.domain.Author;
import ru.otus.spring.library.domain.Book;
import ru.otus.spring.library.repository.AuthorDao;
import ru.otus.spring.library.repository.BookDao;
import ru.otus.spring.library.repository.JpaRepositoryException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Тесты репозитория для работы с книгами")
@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class BookDaoTest {
    private static final String NEW_AUTHOR_NAME = "new author";
    private static final String EXISTING_AUTHOR_NAME = "author_01";
    private static final String NEW_BOOK_TITLE = "new book";
    private static final long NEW_BOOK_ID = 3L;
    private static final long EXISTING_AUTHOR_ID = 1L;
    private static final long EXISTING_BOOK_ID = 1L;
    private static final long DEFAULT_ID = 0L;

    @Autowired
    BookDao bookDao;
    @Autowired
    AuthorDao authorDao;
    @Autowired
    private TestEntityManager tem;

    @Test
    @DisplayName("Должен добавлять книгу и автора, если его еще нет")
    void insertBookAndAuthor() {
        Author author = new Author(DEFAULT_ID, NEW_AUTHOR_NAME);
        Book book = new Book(DEFAULT_ID, NEW_BOOK_TITLE, Collections.singletonList(author));
        authorDao.saveAndFlush(author);
        bookDao.saveAndFlush(book);
        Book expectedBook = tem.find(Book.class, NEW_BOOK_ID);
        assertThat(book).isEqualToComparingFieldByFieldRecursively(expectedBook);
    }

    @Test
    @DisplayName("Должен добавлять книгу и связывать с автором, если он есть")
    void insertBookWhenAuthorExists() {
        Optional<Author> existentAuthor = authorDao.findByNameWithBook(EXISTING_AUTHOR_NAME);
        assertTrue(existentAuthor.isPresent());
        Book book = new Book(DEFAULT_ID, NEW_BOOK_TITLE, Collections.singletonList(existentAuthor.get()));
        bookDao.saveAndFlush(book);
        Book expectedBook = tem.find(Book.class, NEW_BOOK_ID);
        assertThat(book).isEqualToComparingFieldByFieldRecursively(expectedBook);
    }

    @Test
    @DisplayName("Должен изменять поля книги")
    void saveBook() {
        Book book = new Book(EXISTING_BOOK_ID, NEW_BOOK_TITLE, Collections.singletonList(new Author(EXISTING_AUTHOR_ID, EXISTING_AUTHOR_NAME)));
        bookDao.saveAndFlush(book);
        Book expectedBook = tem.find(Book.class, EXISTING_BOOK_ID);
        assertThat(book).isEqualToComparingFieldByFieldRecursively(expectedBook);
    }

    @Test
    @DisplayName("Должен получать существующую книгу")
    void getBookById() {
        Optional<Book> book = bookDao.findById(EXISTING_BOOK_ID);
        Book expectedBook = tem.find(Book.class, EXISTING_BOOK_ID);
        book.ifPresent(value -> assertThat(value).isEqualToComparingFieldByFieldRecursively(expectedBook));
    }


    @Test
    @DisplayName("Должен возвращать Optional.empty при попытке достать несуществующую книгу")
    void getNonexistentBookById() {
        assertEquals(bookDao.findById(NEW_BOOK_ID), Optional.empty());
    }

    @Test
    @DisplayName("Должен удалять существующую книгу, но не трогать авторов")
    void deleteBookById() {
        bookDao.deleteById(EXISTING_BOOK_ID);
        assertEquals(bookDao.findById(EXISTING_BOOK_ID), Optional.empty());
        assertEquals(authorDao.findAll().size(), 3);
    }

    @Test
    @DisplayName("Должен не удалять несуществующую книгу")
    void deleteNonexistentBookById() {
        assertThatThrownBy(() -> bookDao.deleteById(NEW_BOOK_ID)).isInstanceOf(EmptyResultDataAccessException.class);
    }

    @Test
    @DisplayName("Должен получать все книги")
    void getAllBooks() {
        assertThat(bookDao.findAll()).hasSize(2);
    }
}