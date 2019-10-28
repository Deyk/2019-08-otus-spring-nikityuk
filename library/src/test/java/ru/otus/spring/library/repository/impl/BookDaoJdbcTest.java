package ru.otus.spring.library.repository.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.spring.library.domain.Author;
import ru.otus.spring.library.domain.Book;
import ru.otus.spring.library.repository.JdbcRepositoryException;

import static junit.framework.TestCase.assertEquals;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("Тесты jdbc репозитория для работы с книгами")
@JdbcTest
@Import({BookDaoJdbc.class, AuthorDaoJdbc.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class BookDaoJdbcTest {
    private static final String NEW_AUTHOR_NAME = "new author";
    private static final String EXISTING_AUTHOR_NAME = "author_01";
    private static final String NEW_BOOK_TITLE = "new book";
    private static final String EXISTING_BOOK_TITLE = "title_01";
    private static final long NEW_AUTHOR_ID = 4L;
    private static final long NEW_BOOK_ID = 3L;
    private static final long EXISTING_AUTHOR_ID = 1L;
    private static final long EXISTING_BOOK_ID = 1L;
    private static final String SECOND_AUTHOR_NAME = "author_02";
    private static final long SECOND_AUTHOR_ID = 3L;
    private static final String ID = "id";
    private static final String TITLE = "title";

    @Autowired
    BookDaoJdbc bookDaoJdbc;
    @Autowired
    AuthorDaoJdbc authorDaoJdbc;

    @Test
    @DisplayName("Должен добавлять книгу и автора, если его еще нет")
    void insertBookAndAuthor() throws JdbcRepositoryException {
        Book book = bookDaoJdbc.insertBook(NEW_BOOK_TITLE, NEW_AUTHOR_NAME);
        assertThat(book).isNotNull()
                .hasFieldOrPropertyWithValue(ID, NEW_BOOK_ID)
                .hasFieldOrPropertyWithValue(TITLE, NEW_BOOK_TITLE)
                .hasFieldOrProperty("authors");
        assertThat(book.getAuthors()).isNotEmpty().containsOnlyOnce(new Author(NEW_AUTHOR_ID, NEW_AUTHOR_NAME));
    }

    @Test
    @DisplayName("Должен добавлять книгу и связывать с автором, если он есть")
    void insertBookWhenAuthorExists() throws JdbcRepositoryException {
        Book book = bookDaoJdbc.insertBook(NEW_BOOK_TITLE, EXISTING_AUTHOR_NAME);
        assertThat(book).isNotNull()
                .hasFieldOrPropertyWithValue(ID, NEW_BOOK_ID)
                .hasFieldOrPropertyWithValue(TITLE, NEW_BOOK_TITLE)
                .hasFieldOrProperty("authors");
        assertThat(book.getAuthors()).isNotEmpty().containsOnlyOnce(new Author(EXISTING_AUTHOR_ID, EXISTING_AUTHOR_NAME));
    }

    @Test
    @DisplayName("Должен связывать существующую книгу и добавлять и автора, если его еще нет")
    void insertExistingBookAuthor() throws JdbcRepositoryException {
        Book book = bookDaoJdbc.insertBook(EXISTING_BOOK_TITLE, NEW_AUTHOR_NAME);
        assertThat(book).isNotNull()
                .hasFieldOrPropertyWithValue(ID, EXISTING_BOOK_ID)
                .hasFieldOrPropertyWithValue(TITLE, EXISTING_BOOK_TITLE)
                .hasFieldOrProperty("authors");
        assertThat(book.getAuthors()).isNotEmpty().containsOnlyOnce(new Author(NEW_AUTHOR_ID, NEW_AUTHOR_NAME));
    }

    @Test
    @DisplayName("Должен связывать существующую книгу с автором, если он есть")
    void insertExistingBookExistingAuthor() throws JdbcRepositoryException {
        Book book = bookDaoJdbc.insertBook(EXISTING_BOOK_TITLE, EXISTING_AUTHOR_NAME);
        assertThat(book).isNotNull()
                .hasFieldOrPropertyWithValue(ID, EXISTING_BOOK_ID)
                .hasFieldOrPropertyWithValue(TITLE, EXISTING_BOOK_TITLE)
                .hasFieldOrProperty("authors");
        assertThat(book.getAuthors()).isNotEmpty().containsOnlyOnce(new Author(EXISTING_AUTHOR_ID, EXISTING_AUTHOR_NAME));
    }

    @Test
    @DisplayName("Должен изменять поля книги")
    void updateBook() throws JdbcRepositoryException {
        int rowChanged = bookDaoJdbc.updateBook(EXISTING_BOOK_ID, NEW_BOOK_TITLE);
        assertEquals(1, rowChanged);
        Book book = bookDaoJdbc.getBookById(EXISTING_BOOK_ID);
        assertThat(book).isNotNull()
                .hasFieldOrPropertyWithValue(ID, EXISTING_BOOK_ID)
                .hasFieldOrPropertyWithValue(TITLE, NEW_BOOK_TITLE)
                .hasFieldOrProperty("authors");
        assertThat(book.getAuthors()).isNotEmpty().containsOnlyOnce(new Author(EXISTING_AUTHOR_ID, EXISTING_AUTHOR_NAME));
    }

    @Test
    @DisplayName("Должен получать существующую книгу")
    void getBookById() throws JdbcRepositoryException {
        Book book = bookDaoJdbc.getBookById(EXISTING_BOOK_ID);
        assertThat(book).isNotNull()
                .hasFieldOrPropertyWithValue(ID, EXISTING_BOOK_ID)
                .hasFieldOrPropertyWithValue(TITLE, EXISTING_BOOK_TITLE)
                .hasFieldOrProperty("authors");
        assertThat(book.getAuthors()).isNotEmpty().containsOnlyOnce(new Author(EXISTING_AUTHOR_ID, EXISTING_AUTHOR_NAME));
    }

    @Test
    @DisplayName("Должен выбрасывать исключение при попытке достать несуществующую книгу")
    void getNonexistentBookById() {
        assertThatThrownBy(() -> bookDaoJdbc.getBookById(NEW_BOOK_ID)).isInstanceOf(JdbcRepositoryException.class);
    }

    @Test
    @DisplayName("Должен удалять книгу и связанных с ней авторов")
    void deleteBookById() {
        int rowChanged = bookDaoJdbc.deleteBookById(EXISTING_BOOK_ID);
        assertEquals(1, rowChanged);
        assertThatThrownBy(() -> bookDaoJdbc.getBookById(EXISTING_BOOK_ID)).isInstanceOf(JdbcRepositoryException.class);
        assertThat(authorDaoJdbc.getAllAuthors()).isNotEmpty().hasSize(1).containsOnlyOnce(new Author(SECOND_AUTHOR_ID, SECOND_AUTHOR_NAME));
    }

    @Test
    @DisplayName("Должен не удалять несуществующую книгу")
    void deleteNonexistentBookById() {
        int rowChanged = bookDaoJdbc.deleteBookById(NEW_BOOK_ID);
        assertEquals(0, rowChanged);
    }

    @Test
    @DisplayName("Должен получать все книги")
    void getAllBooks() {
        assertThat(bookDaoJdbc.getAllBooks()).hasSize(2);
    }
}