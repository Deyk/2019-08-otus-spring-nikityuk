package ru.otus.spring.library.service;

import ru.otus.spring.library.domain.User;

public interface UserService {

    User login(String login, String password);

}
