package ru.otus.spring.homework0105.service;

import ru.otus.spring.homework0105.domain.User;

import java.util.Scanner;

public interface UserService {

    User createUserFromConsole(Scanner scanner);
}
