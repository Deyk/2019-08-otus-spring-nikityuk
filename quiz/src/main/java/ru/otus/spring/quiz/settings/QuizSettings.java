package ru.otus.spring.quiz.settings;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("quiz")
public class QuizSettings {
    private String filename;
    private String csvDelimiter;
    private int amountOfColumn;

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getCsvDelimiter() {
        return csvDelimiter;
    }

    public void setCsvDelimiter(String csvDelimiter) {
        this.csvDelimiter = csvDelimiter;
    }

    public int getAmountOfColumn() {
        return amountOfColumn;
    }

    public void setAmountOfColumn(int amountOfColumn) {
        this.amountOfColumn = amountOfColumn;
    }
}
