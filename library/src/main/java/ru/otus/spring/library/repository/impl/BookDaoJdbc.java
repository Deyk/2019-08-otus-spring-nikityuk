package ru.otus.spring.library.repository.impl;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.simple.SimpleJdbcInsertOperations;
import org.springframework.stereotype.Repository;
import ru.otus.spring.library.domain.Author;
import ru.otus.spring.library.domain.Book;
import ru.otus.spring.library.repository.BookDao;
import ru.otus.spring.library.repository.JdbcRepositoryException;
import ru.otus.spring.library.repository.ext.AllBooksResultSetExtractor;
import ru.otus.spring.library.repository.ext.BookResultSetExtractor;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class BookDaoJdbc implements BookDao {
    private final NamedParameterJdbcOperations operations;
    private final SimpleJdbcInsertOperations insertBook;

    public BookDaoJdbc(NamedParameterJdbcOperations operations, DataSource dataSource) {
        this.operations = operations;
        this.insertBook = new SimpleJdbcInsert(dataSource).withTableName("book").usingGeneratedKeyColumns("id");
    }

    @Override
    public Book insertBook(String title, long authorId) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("title", title)
                .addValue("author_id", authorId);
        Number key = insertBook.executeAndReturnKey(params);
        Author author = new Author();
        return new Book(key.longValue(), title, Collections.singletonList(author));
    }

    @Override
    public Book updateBook(Book book) {
        return null;
    }

    @Override
    public Book getBookById(long id) throws JdbcRepositoryException {
        return Optional.ofNullable(operations.query(
                "select b.id, b.title, a.id author_id, a.name author_name " +
                        "from book b left join author a on b.id = a.book_id " +
                        "where b.id = :id",
                Collections.singletonMap("id", id), new BookResultSetExtractor()))
                .orElseThrow(() -> new JdbcRepositoryException("Returned book is null"));
    }

    @Override
    public int deleteBookById(long id) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", id);
        return operations.update("delete from book where id = :id", params);
    }

    @Override
    public List<Book> getAllBooks() {
        return operations.query("select * from book", new AllBooksResultSetExtractor());
    }
}
