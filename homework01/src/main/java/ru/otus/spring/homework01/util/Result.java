package ru.otus.spring.homework01.util;

public enum Result {
    A("Excellent"),
    B("Good"),
    C("Satisfactory"),
    D("Poor"),
    E("Awful");

    private String title;

    Result(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
