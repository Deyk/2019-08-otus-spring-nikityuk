package ru.otus.spring.library.service;

import ru.otus.spring.library.domain.Genre;

import java.util.List;

public interface GenreService {
    Genre addGenre(String title);

    Genre getGenreById(long id);

    Genre deleteGenreById(long id);

    List<Genre> getAllGenres();
}
