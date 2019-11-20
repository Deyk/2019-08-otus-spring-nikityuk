package ru.otus.spring.library.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "title", nullable = false)
    private String title;

    @ManyToMany(targetEntity = Author.class, cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    @JoinTable(name = "books_authors", joinColumns = @JoinColumn(name = "book_id"), inverseJoinColumns = @JoinColumn(name = "author_id"))
    private List<Author> authors;

    public Book(long id, String title, List<Author> authors) {
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
