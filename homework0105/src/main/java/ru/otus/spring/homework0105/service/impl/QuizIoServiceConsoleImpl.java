package ru.otus.spring.homework0105.service.impl;

import org.springframework.stereotype.Service;
import ru.otus.spring.homework0105.domain.QuizAnswer;
import ru.otus.spring.homework0105.domain.QuizResult;
import ru.otus.spring.homework0105.domain.QuizUnit;
import ru.otus.spring.homework0105.domain.User;
import ru.otus.spring.homework0105.service.QuizIoService;
import ru.otus.spring.homework0105.util.Result;

import java.util.List;
import java.util.Scanner;
import java.util.UUID;

import static java.lang.System.out;

@Service
public class QuizIoServiceConsoleImpl implements QuizIoService {
    private Scanner scanner;

    public QuizIoServiceConsoleImpl() {
        scanner = new Scanner(System.in);
    }

    @Override
    public void printWelcome() {
        out.printf("%S\n", "Welcome to the Quiz!");
    }

    @Override
    public void printGeneralInfo(int quizSize, User user) {
        out.printf("%S%s %s\n%s%d%s\n%s\n", "Welcome ", user.getName(), user.getSurname(),
                "You will be asked ", quizSize, " questions, ready? Go!",
                "For every questions choose the right answer and enter the number.");
    }

    @Override
    public void printUserAnswer(String userAnswer) {
        out.println("Your answer is " + userAnswer);
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
        out.println("Yes, you are right! +1 point");
    }

    @Override
    public void printWrongAnswerInfo(String rightAnswer) {
        out.println("No, sorry, the right answer is " + rightAnswer);
    }

    @Override
    public void printQuizResult(List<QuizAnswer> quizAnswerList, User user, int score) {
        QuizResult quizResult = new QuizResult(UUID.randomUUID().toString(), user, quizAnswerList, Result.getResult(score));
        out.println("Quiz result is: \n" + quizResult);
    }

    @Override
    public User getUserInfo() {
        String userName, userSurname;
        out.println("Please, enter your name.");
        userName = scanner.nextLine().trim();

        out.println("Please, enter your surname.");
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
                out.println("Please, enter the correct number (from 1 to 4)");
            }
        }
    }
}
