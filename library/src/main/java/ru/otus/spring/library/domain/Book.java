package ru.otus.spring.library.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
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
    @DBRef(lazy = true)
    private List<Comment> comments;

    public Book(String title, List<Author> authors, List<Comment> comments) {
        this.title = title;
        this.authors = authors;
        this.comments = comments;
        this.authors.forEach(author -> author.getBooks().add(this));
        this.comments.forEach(comment -> comment.setBook(this));
    }
}
