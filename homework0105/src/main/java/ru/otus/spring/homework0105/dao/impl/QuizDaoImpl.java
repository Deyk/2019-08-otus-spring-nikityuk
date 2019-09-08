package ru.otus.spring.homework0105.dao.impl;

import org.springframework.util.ResourceUtils;
import ru.otus.spring.homework0105.dao.QuizDao;
import ru.otus.spring.homework0105.dao.ReadQuizException;
import ru.otus.spring.homework0105.domain.QuizResult;
import ru.otus.spring.homework0105.domain.QuizUnit;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static java.lang.System.out;

public class QuizDaoImpl implements QuizDao {

    public static final String QUIZ_FILENAME = "quiz.csv";
    public static final int QUIZ_AMOUNT_OF_COLUMNS = 6;
    private static final String CSV_DELIMITER = ",";

    private String sourceFileName;
    private int amountOfColumns;

    public QuizDaoImpl(String sourceFileName, int amountOfColumns) {
        this.sourceFileName = sourceFileName;
        this.amountOfColumns = amountOfColumns;
    }

    @Override
    public void saveQuizResult(QuizResult result) {
        out.println("Quiz result saved: \n" + result);
    }

    @Override
    public List<QuizUnit> readQuizFromFile() throws ReadQuizException {
        List<QuizUnit> quizUnitList = new ArrayList<>();
        File file;
        try {
            file = ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX + sourceFileName);
        } catch (FileNotFoundException e) {
            throw new ReadQuizException("[ERROR] Can't read the quiz: \n" + e.getMessage());
        }
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] quizStrings = line.split(CSV_DELIMITER);
                if (isGoodFormatted(quizStrings)) {
                    quizUnitList.add(new QuizUnit(UUID.randomUUID().toString(), quizStrings[0],
                            Arrays.asList(Arrays.copyOfRange(quizStrings, 1, quizStrings.length - 1)),
                            Integer.parseInt(quizStrings[quizStrings.length - 1])));
                } else {
                    throw new ReadQuizException("Can't parse question: " + line + "\nPlease, check the quiz source file.");
                }
            }
        } catch (IOException e) {
            e.getMessage();
        }
        return quizUnitList;
    }

    private boolean isGoodFormatted(String[] quizStrings) {
        return quizStrings.length == amountOfColumns && quizStrings[quizStrings.length - 1].matches("[1-4]");
    }
}
