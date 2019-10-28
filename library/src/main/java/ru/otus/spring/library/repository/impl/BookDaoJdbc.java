package ru.otus.spring.library.repository.impl;

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
    private final AuthorDao authorDao;

    public BookDaoJdbc(NamedParameterJdbcOperations operations, DataSource dataSource, AuthorDao authorDao) {
        this.operations = operations;
        this.insertBook = new SimpleJdbcInsert(dataSource).withTableName("book").usingGeneratedKeyColumns("id");
        this.authorDao = authorDao;
    }

    @Override
    public Book insertBook(String title, String authorName) throws JdbcRepositoryException {
        List<Author> uniqueAuthors = authorDao.getAllUniqueAuthors();
        Optional<Book> bookOptional = this.getAllBooks().stream()
                .filter(book -> title.equalsIgnoreCase(book.getTitle()))
                .findAny();

        Optional<Author> authorOptional = uniqueAuthors.stream()
                .filter(ua -> authorName.equalsIgnoreCase(ua.getName()))
                .findFirst();
        Author author;

        if (authorOptional.isPresent()) {
            author = authorOptional.get();
        } else {
            author = authorDao.insertAuthor(authorName);
        }

        if (bookOptional.isPresent()) {
            long bookId = bookOptional.get().getId();
            authorDao.addBookIdToAuthor(author, bookId);
            List<Author> authors = authorDao.getAllAuthorsWithBookId(bookId);
            return new Book(bookId, title, authors);
        } else {
            SqlParameterSource params = new MapSqlParameterSource()
                    .addValue("title", title);
            long bookId = insertBook.executeAndReturnKey(params).longValue();
            authorDao.addBookIdToAuthor(author, bookId);
            return new Book(bookId, title, Collections.singletonList(author));
        }

    }

    @Override
    public int updateBook(long id, String title) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", id)
                .addValue("title", title);
        return operations.update("update book set title = :title where id = :id", params);
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
        return operations.query("" +
                "select b.id, b.title, a.id author_id, a.name author_name " +
                "from book b left join author a on b.id = a.book_id ", new AllBooksResultSetExtractor());
    }
}
