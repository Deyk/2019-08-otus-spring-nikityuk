package ru.otus.spring.homework0105.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.spring.homework0105.domain.User;
import ru.otus.spring.homework0105.service.MessageService;
import ru.otus.spring.homework0105.service.QuizIoService;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
@DisplayName("Класс консольного ввода-вывода")
class QuizIoServiceConsoleImplTest {

    @MockBean
    MessageService messageService;
    @Autowired
    private QuizIoService quizIoService;

    @ParameterizedTest
    @DisplayName("Выводит информацию о пользователе")
    @ValueSource(ints = {Integer.MIN_VALUE, 1, 2, Integer.MAX_VALUE})
    void printGeneralInfo(int inputSize) {
        User user = new User("Name", "Surname");
        quizIoService.printGeneralInfo(inputSize, user);
        verify(messageService, times(1)).printMessage(anyString());
    }
}