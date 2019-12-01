package ru.otus.spring.library.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "comment")
public class Comment {

    @Id
    private String id;
    @Indexed
    @Field("text")
    private String text;
    @Field("date")
    private Date date;
    @DBRef()
    private Book book;

    public Comment(String text, Date date, Book book) {
        this.text = text;
        this.date = date;
        this.book = book;
    }
}
