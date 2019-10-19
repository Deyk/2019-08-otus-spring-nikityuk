package ru.otus.spring.library.dao;

import ru.otus.spring.library.domain.User;

public interface UserDao {
    User getUserByLoginAndPassword(String login, String password);
}
