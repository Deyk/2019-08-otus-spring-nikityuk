package ru.otus.spring.library.repository.ext;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import ru.otus.spring.library.domain.Author;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AuthorResultSetExtractor implements ResultSetExtractor<List<Author>> {
    @Override
    public List<Author> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<Author> authors = new ArrayList<>();
        while (rs.next()) {
            long id = rs.getLong("id");
            String name = rs.getString("name");
            Author author = new Author(id, name);
            authors.add(author);
        }
        return authors;
    }
}
