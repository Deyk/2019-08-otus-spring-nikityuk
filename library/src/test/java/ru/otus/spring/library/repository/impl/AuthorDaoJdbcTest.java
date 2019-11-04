//package ru.otus.spring.library.repository.impl;
//
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
//import org.springframework.context.annotation.Import;
//import org.springframework.test.annotation.DirtiesContext;
//import ru.otus.spring.library.domain.Author;
//
//import static junit.framework.TestCase.assertEquals;
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.assertj.core.api.Assertions.assertThatThrownBy;
//
//@DisplayName("Тесты jdbc репозитория для работы с авторами")
//@JdbcTest
//@Import(AuthorDaoJdbc.class)
//@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
//class AuthorDaoJdbcTest {
//    private static final String NEW_AUTHOR_NAME = "new author";
//    private static final String EXISTING_AUTHOR_NAME = "author_01";
//    private static final long NEW_AUTHOR_ID = 4L;
//    private static final long EXISTING_AUTHOR_ID = 1L;
//    private static final long EXISTING_AUTHOR_ID_2 = 2L;
//    private static final long EXISTENT_BOOK_ID = 1L;
//    private static final String ID = "id";
//    private static final String NAME = "name";
//    @Autowired
//    AuthorDaoJdbc authorDaoJdbc;
//
//    @Test
//    @DisplayName("Должен добавлять автора")
//    void insertAuthor() throws JdbcRepositoryException {
//        Author author = authorDaoJdbc.insertAuthor(NEW_AUTHOR_NAME);
//        assertThat(author).isNotNull()
//                .hasFieldOrPropertyWithValue(ID, NEW_AUTHOR_ID)
//                .hasFieldOrPropertyWithValue(NAME, NEW_AUTHOR_NAME);
//    }
//
//    @Test
//    @DisplayName("Должен добавлять автора")
//    void insertExistentAuthor() {
//        assertThatThrownBy(() -> authorDaoJdbc.insertAuthor(EXISTING_AUTHOR_NAME)).isInstanceOf(JdbcRepositoryException.class);
//    }
//
//    @Test
//    @DisplayName("Должен изменять поля автора")
//    void updateAuthor() throws JdbcRepositoryException {
//        int rowChanged = authorDaoJdbc.updateAuthor(new Author(EXISTING_AUTHOR_ID, NEW_AUTHOR_NAME));
//        assertEquals(1, rowChanged);
//        Author author = authorDaoJdbc.getAuthorById(EXISTING_AUTHOR_ID);
//        assertThat(author).isNotNull()
//                .hasFieldOrPropertyWithValue(ID, EXISTING_AUTHOR_ID)
//                .hasFieldOrPropertyWithValue(NAME, NEW_AUTHOR_NAME);
//    }
//
//    @Test
//    @DisplayName("Должен добавлять поле-ключ к книге к автору")
//    void addBookIdToAuthor() {
//        int rowChanged = authorDaoJdbc.addBookIdToAuthor(new Author(EXISTING_AUTHOR_ID, NEW_AUTHOR_NAME), 1L);
//        assertEquals(1, rowChanged);
//    }
//
//    @Test
//    @DisplayName("Должен получать существующего автора")
//    void getAuthorById() throws JdbcRepositoryException {
//        Author author = authorDaoJdbc.getAuthorById(EXISTING_AUTHOR_ID);
//        assertThat(author).isNotNull()
//                .hasFieldOrPropertyWithValue(ID, EXISTING_AUTHOR_ID)
//                .hasFieldOrPropertyWithValue(NAME, EXISTING_AUTHOR_NAME);
//    }
//
//    @Test
//    @DisplayName("Должен выбрасывать исключение при попытке достать несуществующего автора")
//    void getNonexistentAuthorById() {
//        assertThatThrownBy(() -> authorDaoJdbc.getAuthorById(NEW_AUTHOR_ID)).isInstanceOf(JdbcRepositoryException.class);
//    }
//
//    @Test
//    @DisplayName("Должен не удалять несуществующего автора ")
//    void deleteNonexistentAuthorById() {
//        assertThatThrownBy(() -> authorDaoJdbc.deleteAuthorById(NEW_AUTHOR_ID)).isInstanceOf(JdbcRepositoryException.class);
//    }
//
//    @Test
//    @DisplayName("Должен удалять все записи автора")
//    void deleteAuthorById() throws JdbcRepositoryException {
//        int rowChanged = authorDaoJdbc.deleteAuthorById(EXISTING_AUTHOR_ID_2);
//        assertEquals(2, rowChanged);
//    }
//
//    @Test
//    @DisplayName("Должен вернуть все записи авторов")
//    void getAllAuthors() {
//        assertThat(authorDaoJdbc.getAllAuthors()).hasSize(3);
//    }
//
//    @Test
//    @DisplayName("Должен вернуть только уникальные записи авторов")
//    void getAllUniqueAuthors() {
//        assertThat(authorDaoJdbc.getAllUniqueAuthors()).hasSize(2);
//    }
//
//    @Test
//    @DisplayName("Должен вернуть только записи авторов конкретной книги")
//    void getAllAuthorsWithBookId() {
//        assertThat(authorDaoJdbc.getAllAuthorsWithBookId(EXISTENT_BOOK_ID)).hasSize(2);
//    }
//}