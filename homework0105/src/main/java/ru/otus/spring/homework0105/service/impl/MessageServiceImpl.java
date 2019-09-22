package ru.otus.spring.homework0105.service.impl;

import org.springframework.stereotype.Service;
import ru.otus.spring.homework0105.service.MessageService;

@Service
public class MessageServiceImpl implements MessageService {

    @Override
    public void printMessage(String message) {
        System.out.println(message);
    }
}
