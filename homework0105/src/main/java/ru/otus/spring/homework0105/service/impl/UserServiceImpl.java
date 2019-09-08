package ru.otus.spring.homework0105.service.impl;

import ru.otus.spring.homework0105.domain.User;
import ru.otus.spring.homework0105.service.UserService;

import java.util.Scanner;

import static java.lang.System.out;

public class UserServiceImpl implements UserService {
    @Override
    public User createUserFromConsole(Scanner scanner) {
        String userName, userSurname;
        out.println("Please, enter your name.");
        userName = scanner.nextLine().trim();

        out.println("Please, enter your surname.");
        userSurname = scanner.nextLine().trim();
        return new User(userName, userSurname);
    }
}
