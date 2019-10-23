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
import ru.otus.spring.library.repository.JdbcRepositoryException;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

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
    public int updateAuthor(Author author) throws JdbcRepositoryException {
        String oldName = this.getAuthorById(author.getId()).getName();
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("name", author.getName())
                .addValue("oldName", oldName);
        return operations.update("update author set name = :name where name = :oldName", params);
    }

    @Override
    public Author getAuthorById(long id) throws JdbcRepositoryException {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", id);
        return Optional.ofNullable(
                operations.queryForObject("select * from author where id = :id", params, new AuthorMapper()))
                .orElseThrow(() -> new JdbcRepositoryException("Returned author is null"));
    }

    @Override
    public int deleteAuthorById(long id) throws JdbcRepositoryException {
        String oldName = this.getAuthorById(id).getName();
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("oldName", oldName);
        return operations.update("delete from author where name = :oldName", params);
    }

    @Override
    public List<Author> getAllAuthors() {
        return operations.query("select * from author", new AuthorMapper());
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
