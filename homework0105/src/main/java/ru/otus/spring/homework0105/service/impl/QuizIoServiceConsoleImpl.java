package ru.otus.spring.homework0105.service.impl;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import ru.otus.spring.homework0105.domain.QuizAnswer;
import ru.otus.spring.homework0105.domain.QuizResult;
import ru.otus.spring.homework0105.domain.QuizUnit;
import ru.otus.spring.homework0105.domain.User;
import ru.otus.spring.homework0105.service.QuizIoService;
import ru.otus.spring.homework0105.util.Result;

import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.UUID;

import static java.lang.System.out;

@Service
public class QuizIoServiceConsoleImpl implements QuizIoService {
    private final Scanner scanner;
    private final MessageSource ms;
    private final Locale currentLocale;

    public QuizIoServiceConsoleImpl(final MessageSource ms) {
        this.ms = ms;
        this.currentLocale = LocaleContextHolder.getLocale();
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void printWelcome() {
        out.println(ms.getMessage("quiz.welcome", null, currentLocale));
    }

    @Override
    public void printGeneralInfo(int quizSize, User user) {
        out.println(ms.getMessage("quiz.general.info",
                new String[]{user.getName(), user.getSurname(), Integer.toString(quizSize)}, currentLocale));
    }

    @Override
    public void printUserAnswer(String userAnswer) {
        out.println(ms.getMessage("quiz.user.answer", new String[]{userAnswer}, currentLocale));
    }

    @Override
    public void printQuizUnit(QuizUnit quizUnit, List<String> answers) {
        out.printf("\t%s\n", quizUnit.getQuestion());
        for (int i = 0; i < answers.size(); i++) {
            out.printf("\t%d: %s\n", i + 1, answers.get(i));
        }
    }

    @Override
    public void printRightAnswerInfo() {
        out.println(ms.getMessage("quiz.right.answer.info", null, currentLocale));
    }

    @Override
    public void printWrongAnswerInfo(String rightAnswer) {
        out.println(ms.getMessage("quiz.wrong.answer.info", new String[]{rightAnswer}, currentLocale));
    }

    @Override
    public void printQuizResult(List<QuizAnswer> quizAnswerList, User user, int score) {
        QuizResult quizResult = new QuizResult(UUID.randomUUID().toString(), user, quizAnswerList, Result.getResult(score));
        out.println(ms.getMessage("quiz.result", new String[]{quizResult.toString()}, currentLocale));
    }

    @Override
    public User getUserInfo() {
        String userName, userSurname;
        out.println(ms.getMessage("quiz.user.name", null, currentLocale));
        userName = scanner.nextLine().trim();

        out.println(ms.getMessage("quiz.user.surname", null, currentLocale));
        userSurname = scanner.nextLine().trim();
        return new User(userName, userSurname);
    }

    @Override
    public int getUserAnswer() {
        while (true) {
            String nextLine = scanner.nextLine().trim();
            if (nextLine.matches("[1-4]")) {
                return Integer.parseInt(nextLine);
            } else {
                out.println(ms.getMessage("quiz.correct.number", null, currentLocale));
            }
        }
    }
}
