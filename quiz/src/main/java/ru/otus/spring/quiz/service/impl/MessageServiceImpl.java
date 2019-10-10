package ru.otus.spring.quiz.service.impl;

import org.springframework.stereotype.Service;
import ru.otus.spring.quiz.service.MessageService;

@Service
public class MessageServiceImpl implements MessageService {

    @Override
    public void printMessage(String message) {
        System.out.println(message);
    }
}
