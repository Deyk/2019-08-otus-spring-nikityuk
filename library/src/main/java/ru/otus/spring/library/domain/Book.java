package ru.otus.spring.library.domain;

public class Book {
    private long id;
    private String title;

    public Book(long id, String title) {
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
        return "Book id: " + getId() + ", title: " + getTitle() + "\n";
    }
}
