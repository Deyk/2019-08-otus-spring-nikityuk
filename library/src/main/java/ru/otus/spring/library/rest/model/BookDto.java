package ru.otus.spring.library.rest.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.otus.spring.library.domain.Author;
import ru.otus.spring.library.domain.Book;

import java.util.List;

@Data
@AllArgsConstructor
public class BookDto {

    private String id;
    private String title;
    private List<Author> authors;

    public static BookDto toDto(Book book) {
        return new BookDto(book.getId(), book.getTitle(), book.getAuthors());
    }
}
