package ru.otus.spring.homework0105.service.impl;

import ru.otus.spring.homework0105.dao.QuizDao;
import ru.otus.spring.homework0105.dao.ReadQuizException;
import ru.otus.spring.homework0105.domain.QuizAnswer;
import ru.otus.spring.homework0105.domain.QuizResult;
import ru.otus.spring.homework0105.domain.QuizUnit;
import ru.otus.spring.homework0105.domain.User;
import ru.otus.spring.homework0105.service.QuizRunner;
import ru.otus.spring.homework0105.service.UserService;
import ru.otus.spring.homework0105.util.Result;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

import static java.lang.System.out;

public class QuizRunnerImpl implements QuizRunner {
    private UserService userService;
    private QuizDao quizDao;

    public QuizRunnerImpl(UserService userService, QuizDao quizDao) {
        this.userService = userService;
        this.quizDao = quizDao;
    }

    @Override
    public void startQuiz() {
        try {
            List<QuizUnit> quizUnitList = quizDao.readQuizFromFile();
            List<QuizAnswer> quizAnswerList = new ArrayList<>();
            Scanner scanner = new Scanner(System.in);

            out.printf("%S\n", "Welcome to the Quiz!");
            User user = userService.createUserFromConsole(scanner);

            out.printf("%S%s %s\n%s%d%s\n%s\n", "Welcome ", user.getName(), user.getSurname(),
                    "You will be asked ", quizUnitList.size(), " questions, ready? Go!",
                    "For every questions choose the right answer and enter the number.");

            int score = askQuestions(quizUnitList, quizAnswerList, scanner);
            saveQuizResult(quizAnswerList, user, score);
        } catch (ReadQuizException e) {
            out.println(e.getMessage());
        }
    }

    private int askQuestions(List<QuizUnit> quizUnitList, List<QuizAnswer> quizAnswerList, Scanner scanner) {
        int userAnswer, score = 0;

        for (QuizUnit quizUnit : quizUnitList) {
            List<String> answers = quizUnit.getAnswers();
            printQuizUnit(quizUnit, answers);

            userAnswer = getUserAnswer(scanner);
            quizAnswerList.add(new QuizAnswer(quizUnit.getQuizId(), userAnswer));
            out.println("Your answer is " + answers.get(userAnswer - 1));

            if (checkUserAnswer(userAnswer, quizUnit, answers)) {
                score++;
            }
            out.println();
        }
        return score;
    }

    private void printQuizUnit(QuizUnit quizUnit, List<String> answers) {
        out.printf("\t%s\n", quizUnit.getQuestion());
        for (int i = 0; i < answers.size(); i++) {
            out.printf("\t%d: %s\n", i + 1, answers.get(i));
        }
    }

    private int getUserAnswer(Scanner scanner) {
        while (true) {
            String nextLine = scanner.nextLine().trim();
            if (nextLine.matches("[1-4]")) {
                return Integer.parseInt(nextLine);
            } else {
                out.println("Please, enter the correct number (from 1 to 4)");
            }
        }
    }

    private boolean checkUserAnswer(int userAnswer, QuizUnit quizUnit, List<String> answers) {
        int rightAnswer = quizUnit.getRightAnswer();
        if (userAnswer == rightAnswer) {
            out.println("Yes, you are right! +1 point");
            return true;
        } else {
            out.println("No, sorry, the right answer is " + answers.get(rightAnswer - 1));
            return false;
        }
    }

    private void saveQuizResult(List<QuizAnswer> quizAnswerList, User user, int score) {
        QuizResult quizResult = new QuizResult(UUID.randomUUID().toString(), user, quizAnswerList, Result.getResult(score));
        quizDao.saveQuizResult(quizResult);
    }
}
