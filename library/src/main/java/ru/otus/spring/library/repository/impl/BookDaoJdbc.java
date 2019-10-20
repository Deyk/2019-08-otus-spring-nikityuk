package ru.otus.spring.library.repository.impl;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.simple.SimpleJdbcInsertOperations;
import org.springframework.stereotype.Repository;
import ru.otus.spring.library.domain.Author;
import ru.otus.spring.library.domain.Book;
import ru.otus.spring.library.repository.AuthorDao;
import ru.otus.spring.library.repository.BookDao;
import ru.otus.spring.library.repository.JdbcRepositoryException;
import ru.otus.spring.library.repository.ext.AuthorResultSetExtractor;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class BookDaoJdbc implements BookDao {
    private final AuthorDao authorDao;
    private final NamedParameterJdbcOperations operations;
    private final SimpleJdbcInsertOperations bookInsert;

    public BookDaoJdbc(AuthorDao authorDao, NamedParameterJdbcOperations operations, DataSource dataSource) {
        this.authorDao = authorDao;
        this.operations = operations;
        this.bookInsert = new SimpleJdbcInsert(dataSource).withTableName("book");
    }

    @Override
    public Book insertBook(String title, long authorId) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("title", title)
                .addValue("author_id", authorId);
        Number key = bookInsert.executeAndReturnKey(params);
        Author author = authorDao.getAuthorById(authorId);
        return new Book(key.longValue(), title, Collections.singletonList(author));
    }

    @Override
    public Book updateBook(Book book) {
        return null;
    }

    @Override
    public Book getBookById(long id) throws JdbcRepositoryException {
        Book book = Optional.ofNullable(operations.queryForObject("select * from book where id = :id",
                Collections.singletonMap("id", id), new BookMapper())).orElseThrow(() -> new JdbcRepositoryException("Returned book is null"));

        List<Author> authors = operations.query("select distinct * from author " +
                        "where id in ( select distinct author_id from book where title = :title)",
                Collections.singletonMap("title", book.getTitle()), new AuthorResultSetExtractor());
        book.setAuthors(authors);
        return book;
    }

    @Override
    public Book deleteBookById(long id) {
        return null;
    }

    @Override
    public List<Book> getAllBooks() {
        return null;
    }

    private static class BookMapper implements RowMapper<Book> {

        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            long id = rs.getLong("id");
            String title = rs.getString("title");
            return new Book(id, title, new ArrayList<>());
        }
    }
}
