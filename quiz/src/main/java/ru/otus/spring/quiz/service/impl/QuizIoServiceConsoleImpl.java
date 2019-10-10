package ru.otus.spring.quiz.service.impl;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Service;
import ru.otus.spring.quiz.domain.QuizAnswer;
import ru.otus.spring.quiz.domain.QuizResult;
import ru.otus.spring.quiz.domain.QuizUnit;
import ru.otus.spring.quiz.domain.User;
import ru.otus.spring.quiz.service.MessageService;
import ru.otus.spring.quiz.service.QuizIoService;
import ru.otus.spring.quiz.util.Result;

import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.UUID;

@Service
public class QuizIoServiceConsoleImpl implements QuizIoService {
    private final Scanner scanner;
    private final MessageSource messageSource;
    private final Locale currentLocale;
    private final MessageService messageService;

    public QuizIoServiceConsoleImpl(MessageSource messageSource, MessageService messageService) {
        this.messageSource = messageSource;
        this.messageService = messageService;
        this.currentLocale = LocaleContextHolder.getLocale();
        this.scanner = new Scanner(System.in);
    }

    @Bean
    public static MessageSource messageSource() {
        ReloadableResourceBundleMessageSource ms = new ReloadableResourceBundleMessageSource();
        ms.setBasename("/i18n/bundle");
        ms.setDefaultEncoding("UTF-8");
        return ms;
    }

    @Override
    public void printUserLoginInfo() {
        messageService.printMessage(messageSource.getMessage("quiz.user.login.info", null, currentLocale));
    }

    @Override
    public void printWelcome() {
        messageService.printMessage(messageSource.getMessage("quiz.welcome", null, currentLocale));
    }

    @Override
    public void printGeneralInfo(int quizSize, User user) {
        messageService.printMessage(messageSource.getMessage("quiz.general.info",
                new String[]{user.getName(), user.getSurname(), Integer.toString(quizSize)}, currentLocale));
    }

    @Override
    public void printUserAnswer(String userAnswer) {
        messageService.printMessage(messageSource.getMessage("quiz.user.answer", new String[]{userAnswer}, currentLocale));
    }

    @Override
    public void printQuizUnit(QuizUnit quizUnit, List<String> answers) {
        messageService.printMessage(quizUnit.getQuestion());
        for (int i = 0; i < answers.size(); i++) {
            messageService.printMessage(i + 1 + ") " + answers.get(i));
        }
    }

    @Override
    public void printRightAnswerInfo() {
        messageService.printMessage(messageSource.getMessage("quiz.right.answer.info", null, currentLocale));
    }

    @Override
    public void printWrongAnswerInfo(String rightAnswer) {
        messageService.printMessage(messageSource.getMessage("quiz.wrong.answer.info", new String[]{rightAnswer}, currentLocale));
    }

    @Override
    public void printQuizResult(List<QuizAnswer> quizAnswerList, User user, int score) {
        QuizResult quizResult = new QuizResult(UUID.randomUUID().toString(), user, quizAnswerList, Result.getResult(score));
        messageService.printMessage(messageSource.getMessage("quiz.result", new String[]{quizResult.toString()}, currentLocale));
    }

    @Override
    public int getUserAnswer() {
        while (true) {
            String nextLine = scanner.nextLine().trim();
            if (nextLine.matches("[1-4]")) {
                return Integer.parseInt(nextLine);
            } else {
                messageService.printMessage(messageSource.getMessage("quiz.correct.number", null, currentLocale));
            }
        }
    }

    @Override
    public String getLoginError() {
        return messageSource.getMessage("quiz.login.error", null, currentLocale);
    }
}
