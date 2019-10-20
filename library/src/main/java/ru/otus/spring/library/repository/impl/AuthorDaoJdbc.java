package ru.otus.spring.library.repository.impl;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.simple.SimpleJdbcInsertOperations;
import org.springframework.stereotype.Repository;
import ru.otus.spring.library.domain.Author;
import ru.otus.spring.library.repository.AuthorDao;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class AuthorDaoJdbc implements AuthorDao {
    private final NamedParameterJdbcOperations operations;
    private final SimpleJdbcInsertOperations insertAuthor;

    public AuthorDaoJdbc(NamedParameterJdbcOperations operations, DataSource dataSource) {
        this.operations = operations;
        this.insertAuthor = new SimpleJdbcInsert(dataSource).withTableName("author").usingGeneratedKeyColumns("id");
    }

    @Override
    public Author insertAuthor(String name) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("name", name);
        Number key = insertAuthor.executeAndReturnKey(params);
        return new Author(key.longValue(), name);
    }

    @Override
    public Author updateAuthor(Author author) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", author.getId())
                .addValue("name", author.getName());
        insertAuthor.executeBatch(params);
        return author;
    }

    @Override
    public Author getAuthorById(long id) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", id);
        return operations.queryForObject("select * from author where id = :id", params, new AuthorMapper());
    }

    @Override
    public void deleteAuthorById(long id) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", id);
        operations.update("delete from authors where id = :id", params);
    }

    @Override
    public List<Author> getAllAuthors() {
        return operations.query("select * from authors", new AuthorMapper());
    }

    private static class AuthorMapper implements RowMapper<Author> {
        @Override
        public Author mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            String name = resultSet.getString("name");
            return new Author(id, name);
        }
    }
}
