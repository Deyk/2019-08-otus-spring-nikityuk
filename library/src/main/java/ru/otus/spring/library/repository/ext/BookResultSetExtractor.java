package ru.otus.spring.library.repository.ext;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import ru.otus.spring.library.domain.Author;
import ru.otus.spring.library.domain.Book;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookResultSetExtractor implements ResultSetExtractor<Book> {
    @Override
    public Book extractData(ResultSet rs) throws SQLException, DataAccessException {
        long id = 0;
        String title = "";
        List<Author> authors = new ArrayList<>();

        while (rs.next()) {
            if (id == 0) {
                id = rs.getLong("id");
                title = rs.getString("title");
            }
            long authorId = rs.getLong("author_id");
            String authorName = rs.getString("author_name");
            Author author = new Author(authorId, authorName);
            authors.add(author);
        }
        return id != 0 ? new Book(id, title, authors) : null;
    }
}
