package ru.otus.spring.library.rest.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.spring.library.domain.Author;
import ru.otus.spring.library.domain.Book;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {

    private String id;
    private String title;
    private List<Author> authors;
    private String selectedAuthor;

    public BookDto(String id, String title, String selectedAuthor) {
        this.id = id;
        this.title = title;
        this.selectedAuthor = selectedAuthor;
    }

    public BookDto(Book book) {
        this.id = book.getId();
        this.title = book.getTitle();
        this.authors = book.getAuthors();
    }

    private BookDto(String id, String title, List<Author> authors) {
        this.id = id;
        this.title = title;
        this.authors = authors;
    }

    public static BookDto toDto(Book book) {
        return new BookDto(book.getId(), book.getTitle(), book.getAuthors());
    }
}
