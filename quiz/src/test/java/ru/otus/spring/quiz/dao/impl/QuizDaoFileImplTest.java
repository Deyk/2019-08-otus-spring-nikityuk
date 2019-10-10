package ru.otus.spring.quiz.dao.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.i18n.LocaleContextHolder;
import ru.otus.spring.quiz.dao.QuizDao;
import ru.otus.spring.quiz.dao.ReadQuizException;
import ru.otus.spring.quiz.domain.QuizUnit;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
@DisplayName("Класс взамодействия с данными из файла")
class QuizDaoFileImplTest {

    private static Locale currentLocale = LocaleContextHolder.getLocale();
    @Autowired
    private QuizDao quizDao;

    private static Stream<Arguments> testQuizUnitData() {
        if (currentLocale.equals(Locale.US)) {
            return Stream.of(
                    arguments(new QuizUnit("some id", "In what year was the Colosseum built?",
                            Arrays.asList("72-80", "260-300", "580-605", "1085-1124"), 1)));
        } else {
            return Stream.of(
                    arguments(new QuizUnit("some id", "В какие годы строился Колизей?",
                            Arrays.asList("72-80", "260-300", "580-605", "1085-1124"), 1)));
        }
    }

    @ParameterizedTest
    @DisplayName("Должен считавать вопросы викторины из файла")
    @MethodSource(value = "testQuizUnitData")
    void shouldReadQuizUnitsFromTestFile(QuizUnit testQuizUnitData) throws ReadQuizException {
        List<QuizUnit> testQuizUnits = quizDao.readQuiz();
        assertThat(testQuizUnits).isNotEmpty();
        QuizUnit quizUnit = testQuizUnits.get(0);
        assertThat(quizUnit.getQuestion()).isEqualToIgnoringCase(testQuizUnitData.getQuestion());
        assertThat(quizUnit.getRightAnswer()).isEqualTo(testQuizUnitData.getRightAnswer());
        assertThat(quizUnit.getAnswers()).isEqualTo(testQuizUnitData.getAnswers());
    }

    @Test
    @DisplayName("Должен выбрасывать исключение, если файл не найден")
    void shouldThrowReadExceptionIfFielDoesNotExist() throws ReadQuizException {
        QuizDaoFileImpl quizDaoFile = mock(QuizDaoFileImpl.class);
        when(quizDaoFile.readQuiz()).thenCallRealMethod();
        when(quizDaoFile.getSourceFileName()).thenReturn("XXX");
        assertThatThrownBy(quizDaoFile::readQuiz).isInstanceOf(ReadQuizException.class);
    }

    @Test
    @DisplayName("Должен выбрасывать исключение, если правильный ответ записан неверно")
    void shouldThrowReadExceptionIfRightAnswerNotGoodFormatted() throws ReadQuizException {
        QuizDaoFileImpl quizDaoFile = mock(QuizDaoFileImpl.class);
        when(quizDaoFile.readQuiz()).thenCallRealMethod();
        when(quizDaoFile.getSourceFileName()).thenReturn("quiz-test-right-answer-not-good-formatted");
        assertThatThrownBy(quizDaoFile::readQuiz).isInstanceOf(ReadQuizException.class);
    }

    @Test
    @DisplayName("Должен выбрасывать исключение, если файл содержит неверный разделитель")
    void shouldThrowReadExceptionIfFileHasWrongDelimiter() throws ReadQuizException {
        QuizDaoFileImpl quizDaoFile = mock(QuizDaoFileImpl.class);
        when(quizDaoFile.readQuiz()).thenCallRealMethod();
        when(quizDaoFile.getSourceFileName()).thenReturn("quiz-test-file-has-wrong-delimiter");
        assertThatThrownBy(quizDaoFile::readQuiz).isInstanceOf(ReadQuizException.class);
    }
}