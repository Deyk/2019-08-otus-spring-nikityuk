package ru.otus.spring.library.repository.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.spring.library.domain.Author;
import ru.otus.spring.library.domain.Book;
import ru.otus.spring.library.repository.JpaRepositoryException;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("Тесты jpa репозитория для работы с книгами")
@DataJpaTest
@Import({BookDaoJpa.class, AuthorDaoJpa.class, CommentDaoJpa.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class BookDaoJdbcTest {
    private static final String NEW_AUTHOR_NAME = "new author";
    private static final String EXISTING_AUTHOR_NAME = "author_01";
    private static final String NEW_BOOK_TITLE = "new book";
    private static final long NEW_BOOK_ID = 3L;
    private static final long EXISTING_AUTHOR_ID = 1L;
    private static final long EXISTING_BOOK_ID = 1L;
    private static final long EXISTING_COMMENT_ID = 1L;
    private static final String THIRD_AUTHOR_NAME = "author_03";
    private static final long THIRD_AUTHOR_ID = 3L;
    private static final long DEFAULT_ID = 0L;

    @Autowired
    BookDaoJpa bookDaoJpa;
    @Autowired
    AuthorDaoJpa authorDaoJpa;
    @Autowired
    CommentDaoJpa commentDaoJpa;
    @Autowired
    private TestEntityManager tem;

    @Test
    @DisplayName("Должен добавлять книгу и автора, если его еще нет")
    void insertBookAndAuthor() {
        Book book = new Book(DEFAULT_ID, NEW_BOOK_TITLE, Collections.singletonList(new Author(DEFAULT_ID, NEW_AUTHOR_NAME)));
        bookDaoJpa.saveBook(book);
        Book expectedBook = tem.find(Book.class, NEW_BOOK_ID);
        assertThat(book).isEqualToComparingFieldByFieldRecursively(expectedBook);
    }

    @Test
    @DisplayName("Должен добавлять книгу и связывать с автором, если он есть")
    void insertBookWhenAuthorExists() {
        Book book = new Book(DEFAULT_ID, NEW_BOOK_TITLE, Collections.singletonList(new Author(DEFAULT_ID, EXISTING_AUTHOR_NAME)));
        bookDaoJpa.saveBook(book);
        Book expectedBook = tem.find(Book.class, NEW_BOOK_ID);
        assertThat(book).isEqualToComparingFieldByFieldRecursively(expectedBook);
    }

    @Test
    @DisplayName("Должен изменять поля книги")
    void saveBook() {
        Book book = new Book(EXISTING_BOOK_ID, NEW_BOOK_TITLE, Collections.singletonList(new Author(EXISTING_AUTHOR_ID, EXISTING_AUTHOR_NAME)));
        bookDaoJpa.saveBook(book);
        Book expectedBook = tem.find(Book.class, EXISTING_BOOK_ID);
        assertThat(book).isEqualToComparingFieldByFieldRecursively(expectedBook);
    }

    @Test
    @DisplayName("Должен получать существующую книгу")
    void getBookById() throws JpaRepositoryException {
        Book book = bookDaoJpa.getBookById(EXISTING_BOOK_ID);
        Book expectedBook = tem.find(Book.class, EXISTING_BOOK_ID);
        assertThat(book).isEqualToComparingFieldByFieldRecursively(expectedBook);
    }

    @Test
    @DisplayName("Должен выбрасывать исключение при попытке достать несуществующую книгу")
    void getNonexistentBookById() {
        assertThatThrownBy(() -> bookDaoJpa.getBookById(NEW_BOOK_ID)).isInstanceOf(JpaRepositoryException.class);
    }

    @Test
    @DisplayName("Должен удалять книгу и связанных с ней авторов")
    void deleteBookById() throws JpaRepositoryException {
        bookDaoJpa.deleteBookById(EXISTING_BOOK_ID);
        assertThatThrownBy(() -> bookDaoJpa.getBookById(EXISTING_BOOK_ID)).isInstanceOf(JpaRepositoryException.class);
        assertThat(authorDaoJpa.getAllAuthors()).isNotEmpty().hasSize(1).containsOnlyOnce(new Author(THIRD_AUTHOR_ID, THIRD_AUTHOR_NAME));
        assertThatThrownBy(() -> commentDaoJpa.getCommentById(EXISTING_COMMENT_ID)).isInstanceOf(JpaRepositoryException.class);
    }

    @Test
    @DisplayName("Должен не удалять несуществующую книгу")
    void deleteNonexistentBookById() {
        assertThatThrownBy(() -> bookDaoJpa.deleteBookById(NEW_BOOK_ID)).isInstanceOf(JpaRepositoryException.class);
    }

    @Test
    @DisplayName("Должен получать все книги")
    void getAllBooks() {
        assertThat(bookDaoJpa.getAllBooks()).hasSize(2);
    }
}