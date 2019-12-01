package ru.otus.spring.library.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Data
@NoArgsConstructor
@Document(collection = "book")
public class Book {
    @Id
    private String id;
    @Field("title")
    private String title;

    private List<Author> authors;

    public Book(String id, String title, List<Author> authors) {
        this.id = id;
        this.title = title;
        this.authors = authors;
        this.authors.forEach(author -> author.getBooks().add(this));
    }

    public Book(String title, List<Author> authors) {
        this.title = title;
        this.authors = authors;
        this.authors.forEach(author -> author.getBooks().add(this));
    }
}
