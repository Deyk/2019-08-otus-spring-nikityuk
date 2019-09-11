package ru.otus.spring.homework0105.service.impl;

import org.springframework.stereotype.Service;
import ru.otus.spring.homework0105.dao.QuizDao;
import ru.otus.spring.homework0105.dao.ReadQuizException;
import ru.otus.spring.homework0105.domain.QuizAnswer;
import ru.otus.spring.homework0105.domain.QuizUnit;
import ru.otus.spring.homework0105.domain.User;
import ru.otus.spring.homework0105.service.QuizIoService;
import ru.otus.spring.homework0105.service.QuizRunner;

import java.util.ArrayList;
import java.util.List;

import static java.lang.System.out;

@Service
public class QuizRunnerImpl implements QuizRunner {
    private QuizDao quizDao;
    private QuizIoService quizIoService;

    public QuizRunnerImpl(QuizDao quizDao, QuizIoService quizIoService) {
        this.quizDao = quizDao;
        this.quizIoService = quizIoService;
    }

    @Override
    public void startQuiz() {
        try {
            List<QuizUnit> quizUnitList = quizDao.readQuiz();
            List<QuizAnswer> quizAnswerList = new ArrayList<>();

            quizIoService.printWelcome();
            User user = quizIoService.getUserInfo();

            quizIoService.printGeneralInfo(quizUnitList.size(), user);

            int score = askQuestions(quizUnitList, quizAnswerList);
            quizIoService.printQuizResult(quizAnswerList, user, score);
        } catch (ReadQuizException e) {
            out.println(e.getMessage());
        }
    }

    private int askQuestions(List<QuizUnit> quizUnitList, List<QuizAnswer> quizAnswerList) {
        int userAnswer, score = 0;

        for (QuizUnit quizUnit : quizUnitList) {
            List<String> answers = quizUnit.getAnswers();
            quizIoService.printQuizUnit(quizUnit, answers);

            userAnswer = quizIoService.getUserAnswer();
            quizAnswerList.add(new QuizAnswer(quizUnit.getQuizId(), userAnswer));
            quizIoService.printUserAnswer(answers.get(userAnswer - 1));

            if (checkUserAnswer(userAnswer, quizUnit, answers)) {
                score++;
            }
            out.println();
        }
        return score;
    }


    private boolean checkUserAnswer(int userAnswer, QuizUnit quizUnit, List<String> answers) {
        int rightAnswer = quizUnit.getRightAnswer();
        if (userAnswer == rightAnswer) {
            quizIoService.printRightAnswerInfo();
            return true;
        } else {
            quizIoService.printWrongAnswerInfo(answers.get(rightAnswer - 1));
            return false;
        }
    }
}
