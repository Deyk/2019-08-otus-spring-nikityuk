package ru.otus.spring.library.domain;

public class Author {
    private long id;
    private String name;
    private String surname;
    private int age;

    public Author(long id, String name, String surname, int age) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.age = age;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public int getAge() {
        return age;
    }

    @Override
    public String toString() {
        return "Author id: " + getId() + ", name: " + getName() + ", surname: " + getSurname() + ", age: " + getAge() + "\n";
    }
}
