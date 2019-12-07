package ru.otus.spring.library.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "book")
public class Book {
    @Id
    private String id;
    @Field("title")
    private String title;

    private List<Author> authors;
    @DBRef(lazy = true)
    private List<Comment> comments;

    @PersistenceConstructor
    public Book(String title, List<Author> authors, List<Comment> comments) {
        this.title = title;
        this.authors = authors;
        this.comments = comments;
    }
}
