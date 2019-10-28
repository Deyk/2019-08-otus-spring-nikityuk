package ru.otus.spring.library.repository.impl;

import org.springframework.dao.IncorrectResultSizeDataAccessException;
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
import ru.otus.spring.library.repository.ext.AllUniqueAuthorsResultSetExtractor;

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
    public Author insertAuthor(String name) throws JdbcRepositoryException {
        if (this.getAllUniqueAuthors().stream().anyMatch(author -> name.equalsIgnoreCase(author.getName()))) {
            throw new JdbcRepositoryException("Author already exists");
        }
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("name", name);
        long authorId = insertAuthor.executeAndReturnKey(params).longValue();
        return new Author(authorId, name);
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
    public int addBookIdToAuthor(Author author, long bookId) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", author.getId())
                .addValue("bookId", bookId);
        return operations.update("update author set book_id = :bookId where id = :id", params);
    }

    @Override
    public Author getAuthorById(long id) throws JdbcRepositoryException {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", id);
        try {
            return operations.queryForObject("select * from author where id = :id", params, new AuthorMapper());
        } catch (IncorrectResultSizeDataAccessException e) {
            throw new JdbcRepositoryException("Returned author is null");
        }

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

    @Override
    public List<Author> getAllUniqueAuthors() {
        return operations.query("select * from author", new AllUniqueAuthorsResultSetExtractor());
    }

    @Override
    public List<Author> getAllAuthorsWithBookId(long bookId) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("bookId", bookId);
        return operations.query("select * from author where book_id = :bookId", params, new AuthorMapper());
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
