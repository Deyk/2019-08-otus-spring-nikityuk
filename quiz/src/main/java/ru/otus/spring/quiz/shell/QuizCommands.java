package ru.otus.spring.quiz.shell;

import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;
import org.springframework.util.StringUtils;
import ru.otus.spring.quiz.domain.User;
import ru.otus.spring.quiz.service.QuizIoService;
import ru.otus.spring.quiz.service.QuizRunner;

@ShellComponent
public class QuizCommands {
    private final QuizRunner quizRunner;
    private final QuizIoService quizIoService;

    private User user;

    public QuizCommands(QuizRunner quizRunner, QuizIoService quizIoService) {
        this.quizRunner = quizRunner;
        this.quizIoService = quizIoService;
    }

    @ShellMethod(value = "Login command", key = {"l", "login"})
    public void login(@ShellOption(defaultValue = "user") String name, String surname) {
        user = new User(name, surname);
    }

    @ShellMethod(value = "Start quiz", key = {"s", "start"})
    @ShellMethodAvailability(value = "isUserLoggedIn")
    public void startQuiz() {
        quizRunner.startQuiz(user);
    }

    private Availability isUserLoggedIn() {
        return this.user == null || StringUtils.isEmpty(this.user.getName()) || StringUtils.isEmpty(this.user.getSurname())
                ? Availability.unavailable(quizIoService.getLoginError())
                : Availability.available();
    }
}
