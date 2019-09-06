package ru.otus.spring.homework0105.service.impl;

import org.springframework.lang.NonNull;
import org.springframework.util.ResourceUtils;
import ru.otus.spring.homework0105.domain.QuizSourceFile;
import ru.otus.spring.homework0105.domain.QuizUnit;
import ru.otus.spring.homework0105.service.FileReader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

import static ru.otus.spring.homework0105.util.Constants.CSV_DELIMETER;

public class FileReaderImpl implements FileReader {
    @Override
    public List<QuizUnit> readQuiz(@NonNull QuizSourceFile quizSourceFile) throws FileNotFoundException {
        Random random = new Random();
        List<QuizUnit> quizUnitList = new ArrayList<>();
        File file = ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX + quizSourceFile.getFilename());
        try (BufferedReader br = new BufferedReader(new java.io.FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] quizStrings = line.split(CSV_DELIMETER);
                quizUnitList.add(new QuizUnit(random.nextInt(), quizStrings[0],
                        Arrays.asList(Arrays.copyOfRange(quizStrings, 1, quizStrings.length - 1)),
                        Integer.parseInt(quizStrings[quizStrings.length - 1])));
            }
        } catch (IOException e) {
            e.getMessage();
        }
        return quizUnitList;
    }
}
