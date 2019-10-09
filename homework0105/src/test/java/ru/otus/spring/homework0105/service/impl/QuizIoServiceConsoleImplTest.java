package ru.otus.spring.homework0105.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import ru.otus.spring.homework0105.domain.QuizUnit;
import ru.otus.spring.homework0105.domain.User;
import ru.otus.spring.homework0105.service.MessageService;
import ru.otus.spring.homework0105.service.QuizIoService;
import ru.otus.spring.homework0105.service.QuizRunner;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@DisplayName("Класс консольного ввода-вывода")
class QuizIoServiceConsoleImplTest {

    @MockBean
    MessageService messageService;
    @MockBean
    MessageSource messageSource;
    @MockBean
    QuizRunner quizRunner;

    @Mock
    QuizUnit quizUnit;

    @Autowired
    private QuizIoService quizIoService;

    @ParameterizedTest
    @DisplayName("Выводит информацию о пользователе")
    @ValueSource(ints = {Integer.MIN_VALUE, 0, Integer.MAX_VALUE})
    void printGeneralInfo(int inputSize) {
        User user = new User("Name", "Surname");
        quizIoService.printGeneralInfo(inputSize, user);
        verify(messageService, times(1)).printMessage(any());
        verify(messageSource, times(1)).getMessage(any(), any(), any());
    }

    @ParameterizedTest
    @DisplayName("Выводит вопросы теста")
    @MethodSource(value = "printQuizUnitTestValues")
    void printQuizUnit(List<String> inputQuizAnswers) {
        when(quizUnit.getQuestion()).thenReturn("");
        quizIoService.printQuizUnit(quizUnit, inputQuizAnswers);
        verify(messageService, times(4)).printMessage(anyString());
    }

    private static Stream<Arguments> printQuizUnitTestValues() {
        return Stream.of(
                arguments(Arrays.asList("First", "Second", "Third")),
                arguments(Arrays.asList("1'st", "2'nd", "3'rd"))
        );
    }
}