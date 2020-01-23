package ru.otus.spring.library.rest.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.otus.spring.library.domain.Author;

@Data
@AllArgsConstructor
public class AuthorDto {

    private String id;
    private String name;

    public AuthorDto(Author author) {
        this.id = author.getId();
        this.name = author.getName();
    }

    public static AuthorDto toDto(Author author) {
        return new AuthorDto(author.getId(), author.getName());
    }
}
