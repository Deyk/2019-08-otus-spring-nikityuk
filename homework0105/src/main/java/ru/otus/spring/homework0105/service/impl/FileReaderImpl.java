package ru.otus.spring.homework0105.service.impl;

import org.springframework.util.ResourceUtils;
import ru.otus.spring.homework0105.domain.QuizUnit;
import ru.otus.spring.homework0105.service.FileReader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static ru.otus.spring.homework0105.util.Constants.CSV_DELIMITER;

public class FileReaderImpl implements FileReader {
    private String sourceFileName;
    private int amountOfColumns;

    public FileReaderImpl(String sourceFileName, int amountOfColumns) {
        this.sourceFileName = sourceFileName;
        this.amountOfColumns = amountOfColumns;
    }

    @Override
    public List<QuizUnit> readQuiz() throws FileNotFoundException {
        List<QuizUnit> quizUnitList = new ArrayList<>();
        File file = ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX + sourceFileName);
        try (BufferedReader br = new BufferedReader(new java.io.FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] quizStrings = line.split(CSV_DELIMITER);
                if (isGoodFormatted(quizStrings)) {
                    quizUnitList.add(new QuizUnit(UUID.randomUUID().toString(), quizStrings[0],
                            Arrays.asList(Arrays.copyOfRange(quizStrings, 1, quizStrings.length - 1)),
                            Integer.parseInt(quizStrings[quizStrings.length - 1])));
                } else {
                    System.out.printf("%s\n%s\n%s\n", "Can't parse question: ", line, "Please, check the quiz source file.");
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
