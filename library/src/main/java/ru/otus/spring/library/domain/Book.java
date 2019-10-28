package ru.otus.spring.library.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class Book {
    private long id;
    private String title;
    private List<Author> authors;
}
