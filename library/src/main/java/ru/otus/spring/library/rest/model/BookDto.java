package ru.otus.spring.library.rest.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.otus.spring.library.domain.Author;
import ru.otus.spring.library.domain.Book;

import java.util.List;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class BookDto {

    private final String id;
    private final String title;
    private final List<Author> authors;
    private String selectedAuthor;

    public BookDto(Book book) {
        this.id = book.getId();
        this.title = book.getTitle();
        this.authors = book.getAuthors();
    }

    public static BookDto toDto(Book book) {
        return new BookDto(book.getId(), book.getTitle(), book.getAuthors());
    }
}
