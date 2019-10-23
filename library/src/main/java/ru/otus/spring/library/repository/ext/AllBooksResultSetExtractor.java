package ru.otus.spring.library.repository.ext;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import ru.otus.spring.library.domain.Author;
import ru.otus.spring.library.domain.Book;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AllBooksResultSetExtractor implements ResultSetExtractor<List<Book>> {

    @Override
    public List<Book> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<Book> books = new ArrayList<>();
        List<Author> authors = new ArrayList<>();
        Book book = null;

        while (rs.next()) {
            long id = rs.getLong("id");
            if (book == null || id != book.getId()) {
                String title = rs.getString("title");
                authors = new ArrayList<>();
                extractAuthor(rs, authors);
                book = new Book(id, title, authors);
                books.add(book);
            } else {
                extractAuthor(rs, authors);
            }
        }
        return books;
    }

    private void extractAuthor(ResultSet rs, List<Author> authors) throws SQLException {
        long authorId = rs.getLong("author_id");
        String authorName = rs.getString("author_name");
        Author author = new Author(authorId, authorName);
        authors.add(author);
    }
}
