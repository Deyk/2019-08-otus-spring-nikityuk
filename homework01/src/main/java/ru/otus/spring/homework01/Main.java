package ru.otus.spring.homework01;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.ResourceUtils;
import ru.otus.spring.homework01.domain.QuizUnit;
import ru.otus.spring.homework01.domain.User;
import ru.otus.spring.homework01.util.CsvFile;

import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

import static java.lang.System.out;
import static ru.otus.spring.homework01.util.Constants.CSV_DELIMETER;

public class Main {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");
        CsvFile csvFile = (CsvFile) context.getBean("csvFile");

        try (Scanner scanner = new Scanner(System.in)) {
            File file = ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX + csvFile.getFilename());
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line = br.readLine();
                while (line != null && !line.equalsIgnoreCase("End") ) {
                    String[] quizStrings = line.split(CSV_DELIMETER);
                    QuizUnit quizUnit = new QuizUnit(quizStrings[0],
                            Arrays.asList(Arrays.copyOfRange(quizStrings, 1, quizStrings.length - 1)),
                            Integer.parseInt(quizStrings[quizStrings.length - 1]));

                    out.printf("%S%n%s%n%s%n", "Welcome to the Quiz!", "Anytime you enter 'end' to close the dialog",
                            "Please, enter your name...");
                    String userName = scanner.nextLine().trim();

                    out.printf("%s%n", "Please, enter your surname...");
                    String userSurname = scanner.nextLine().trim();

                    User user = new User(userName, userSurname);

                    out.printf("%s  %s%n", user.getName(), user.getSurname());

                }
            } catch (IOException e) {
                e.getMessage();
            }
        } catch (FileNotFoundException e) {
            e.getMessage();
        }
    }
}
