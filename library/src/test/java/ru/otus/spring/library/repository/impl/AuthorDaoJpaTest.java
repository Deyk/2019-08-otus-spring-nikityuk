package ru.otus.spring.library.repository.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.spring.library.domain.Author;
import ru.otus.spring.library.repository.JpaRepositoryException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("Тесты jpa репозитория для работы с авторами")
@DataJpaTest
@Import(AuthorDaoJpa.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class AuthorDaoJpaTest {
    private static final String NEW_AUTHOR_NAME = "new author";
    private static final long NEW_AUTHOR_ID = 4L;
    private static final long EXISTING_AUTHOR_ID = 1L;
    private static final long EXISTING_AUTHOR_ID_2 = 2L;
    private static final long EXISTING_AUTHOR_ID_3 = 3L;
    private static final long DEFAULT_ID = 0L;

    @Autowired
    AuthorDaoJpa authorDaoJpa;
    @Autowired
    private TestEntityManager tem;

    @Test
    @DisplayName("Должен добавлять автора")
    void insertAuthor() {
        Author author = authorDaoJpa.saveAuthor(new Author(DEFAULT_ID, NEW_AUTHOR_NAME));
        Author expectedAuthor = tem.find(Author.class, NEW_AUTHOR_ID);
        assertThat(author).isEqualToComparingFieldByFieldRecursively(expectedAuthor);
    }

    @Test
    @DisplayName("Должен изменять поля автора")
    void saveAuthor() {
        Author author = authorDaoJpa.saveAuthor(new Author(EXISTING_AUTHOR_ID, NEW_AUTHOR_NAME));
        Author expectedAuthor = tem.find(Author.class, EXISTING_AUTHOR_ID);
        assertThat(author).isEqualToComparingFieldByFieldRecursively(expectedAuthor);
    }

    @Test
    @DisplayName("Должен получать существующего автора")
    void getAuthorById() throws JpaRepositoryException {
        Author author = authorDaoJpa.getAuthorById(EXISTING_AUTHOR_ID);
        Author expectedAuthor = tem.find(Author.class, EXISTING_AUTHOR_ID);
        assertThat(author).isEqualToComparingFieldByFieldRecursively(expectedAuthor);
    }

    @Test
    @DisplayName("Должен выбрасывать исключение при попытке достать несуществующего автора")
    void getNonexistentAuthorById() {
        assertThatThrownBy(() -> authorDaoJpa.getAuthorById(NEW_AUTHOR_ID)).isInstanceOf(JpaRepositoryException.class);
    }

    @Test
    @DisplayName("Должен не удалять несуществующего автора ")
    void deleteNonexistentAuthorById() {
        assertThatThrownBy(() -> authorDaoJpa.deleteAuthorById(NEW_AUTHOR_ID)).isInstanceOf(JpaRepositoryException.class);
    }

    @Test
    @DisplayName("Должен удалять все записи автора")
    void deleteAuthorWithBookById() throws JpaRepositoryException {
        authorDaoJpa.deleteAuthorById(EXISTING_AUTHOR_ID_2);
        assertThatThrownBy(() -> authorDaoJpa.getAuthorById(EXISTING_AUTHOR_ID_2)).isInstanceOf(JpaRepositoryException.class);
    }

    @Test
    @DisplayName("Должен удалять все записи автора без книг")
    void deleteAuthorById() throws JpaRepositoryException {
        authorDaoJpa.deleteAuthorById(EXISTING_AUTHOR_ID_3);
        assertThatThrownBy(() -> authorDaoJpa.getAuthorById(EXISTING_AUTHOR_ID_3)).isInstanceOf(JpaRepositoryException.class);
    }

    @Test
    @DisplayName("Должен вернуть все записи авторов")
    void getAllAuthors() {
        assertThat(authorDaoJpa.getAllAuthors()).hasSize(3);
    }
}