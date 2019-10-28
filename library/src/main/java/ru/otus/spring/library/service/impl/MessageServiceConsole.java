package ru.otus.spring.library.service.impl;

import org.springframework.stereotype.Service;
import ru.otus.spring.library.service.MessageService;

import static java.lang.System.out;

@Service
public class MessageServiceConsole implements MessageService {

    @Override
    public void printMessage(String message) {
        out.println(message);
    }
}
