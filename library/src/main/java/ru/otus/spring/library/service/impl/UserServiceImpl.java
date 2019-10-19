package ru.otus.spring.library.service.impl;

import org.springframework.stereotype.Service;
import ru.otus.spring.library.dao.UserDao;
import ru.otus.spring.library.domain.User;
import ru.otus.spring.library.service.UserService;

@Service
public class UserServiceImpl implements UserService {
    private final UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User login(String login, String password) {
        return userDao.getUserByLoginAndPassword(login, password);
    }
}
