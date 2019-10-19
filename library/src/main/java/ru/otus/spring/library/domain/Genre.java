package ru.otus.spring.library.domain;

public class Genre {
    private long id;
    private String title;

    public Genre(long id, String title) {
        this.id = id;
        this.title = title;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return "Genre id: " + getId() + ", title: " + getTitle() + "\n";
    }
}
