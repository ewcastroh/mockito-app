package com.ewch.mockito.app.service;

import com.ewch.mockito.app.model.Exam;
import com.ewch.mockito.app.repository.ExamRepository;
import com.ewch.mockito.app.repository.ExamRepositoryImpl;
import com.ewch.mockito.app.repository.QuestionRepository;
import com.ewch.mockito.app.repository.QuestionRepositoryImpl;
import com.ewch.mockito.app.utils.Data;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNotNull;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExamServiceImplTest {

    @InjectMocks
    private ExamServiceImpl examService;

    @Mock
    private ExamRepositoryImpl examRepository;

    @Mock
    private QuestionRepositoryImpl questionRepository;

    @Captor
    ArgumentCaptor<Long> argumentCaptor;

    @BeforeEach
    void setUp() {
        //MockitoAnnotations.openMocks(this);
        /*this.examRepository = mock(ExamRepository.class);
        this.questionRepository = mock(QuestionRepository.class);
        this.examService = new ExamServiceImpl(examRepository, questionRepository);*/
    }

    @Test
    void findExamByNameTest() {
        // Given
        Exam expectedExam = new Exam(5L, "Math");

        when(examRepository.findAllExams()).thenReturn(Data.EXAM_LIST);

        // When
        Optional<Exam> actual = examService.findExamByName("Math");

        // Then
        assertAll(
                () -> assertTrue(actual.isPresent()),
                () -> assertEquals(expectedExam.getId(), actual.orElseThrow().getId()),
                () -> assertEquals(expectedExam.getName(), actual.orElseThrow().getName())
        );
    }

    @Test
    void findExamByNameWithEmptyListTest() {
        // Given
        List<Exam> examList = Collections.emptyList();

        when(examRepository.findAllExams()).thenReturn(examList);

        // When
        Optional<Exam> actual = examService.findExamByName("Math");

        // Then
        assertAll(
                () -> assertFalse(actual.isPresent()),
                () -> assertTrue(examRepository.findAllExams().isEmpty())

        );
    }

    @Test
    void findExamByNameWithQuestionsTest() {
        // Given
        Exam exam = new Exam(5L, "Math");
        int expectedQuestionsCount = 5;
        String expectedQuestion = "Arithmetic";
        when(examRepository.findAllExams()).thenReturn(Data.EXAM_LIST);
        when(questionRepository.findQuestionsByExamId(anyLong())).thenReturn(Data.QUESTION_LIST);

        // When
        Exam actual = examService.findExamByNameWithQuestions(exam.getName());

        // Then
        assertAll(
                () -> assertEquals(expectedQuestionsCount, actual.getQuestions().size()),
                () -> assertTrue(actual.getQuestions().contains(expectedQuestion))
        );
    }

    @Test
    void findExamByNameWithQuestionsVerifyTest() {
        // Given
        Exam exam = new Exam(5L, "Math");
        int expectedQuestionsCount = 5;
        String expectedQuestion = "Arithmetic";
        when(examRepository.findAllExams()).thenReturn(Data.EXAM_LIST);
        when(questionRepository.findQuestionsByExamId(anyLong())).thenReturn(Data.QUESTION_LIST);

        // When
        Exam actual = examService.findExamByNameWithQuestions(exam.getName());

        // Then
        assertAll(
                () -> assertEquals(expectedQuestionsCount, actual.getQuestions().size()),
                () -> assertTrue(actual.getQuestions().contains(expectedQuestion)),
                () -> verify(examRepository).findAllExams(),
                () -> verify(questionRepository).findQuestionsByExamId(anyLong())
        );
    }

    @Test
    void findExamByNameWithQuestionsListEmptyVerifyTest() {
        // Given
        Exam exam = new Exam(5L, "Math2");
        when(examRepository.findAllExams()).thenReturn(Data.EXAM_LIST);
        when(questionRepository.findQuestionsByExamId(anyLong())).thenReturn(Data.QUESTION_LIST);

        // When
        Exam actual = examService.findExamByNameWithQuestions(exam.getName());

        // Then
        assertAll(
                () -> assertNull(actual),
                () -> verify(examRepository).findAllExams(),
                () -> verify(questionRepository).findQuestionsByExamId(anyLong())
        );
    }

    @Test
    void saveExamTest() {
        // Given
        Exam expectedExam = new Exam(8L, "Physics");
        expectedExam.setQuestions(Data.QUESTION_LIST);

        when(examRepository.save(any(Exam.class))).then(new Answer<Exam>() {
            Long sequence = 8L;
            @Override
            public Exam answer(InvocationOnMock invocationOnMock) throws Throwable {
                Exam exam = invocationOnMock.getArgument(0);
                exam.setId(sequence++);
                return exam;
            }
        });

        // When
        Exam actual = examService.save(expectedExam);

        // Then
        assertAll(
                () -> assertNotNull(actual),
                () -> assertEquals(expectedExam.getId(), actual.getId()),
                () -> assertEquals(expectedExam.getName(), actual.getName()),
                () -> verify(examRepository).save(any(Exam.class)),
                () -> verify(questionRepository).saveQuestionList(anyList())
        );
    }

    @Test
    void handleExceptionTest() {
        // Given
        Exam exam = new Exam(5L, "Math");
        when(examRepository.findAllExams()).thenReturn(Data.EXAM_LIST);
        when(questionRepository.findQuestionsByExamId(anyLong())).thenThrow(IllegalArgumentException.class);

        // Then
        assertAll(
                () -> assertThrows(IllegalArgumentException.class, () -> examService.findExamByNameWithQuestions(exam.getName()))
        );
    }

    @Test
    void handleExceptionWithNullMatchersTest() {
        // Given
        Exam exam = new Exam(5L, "Math");
        when(examRepository.findAllExams()).thenReturn(Data.EXAM_LIST_WITH_NULL_IDS);
        when(questionRepository.findQuestionsByExamId(isNull())).thenThrow(IllegalArgumentException.class);

        // Then
        assertAll(
                () -> assertThrows(IllegalArgumentException.class, () -> examService.findExamByNameWithQuestions(exam.getName())),
                () -> verify(examRepository).findAllExams(),
                () -> verify(questionRepository).findQuestionsByExamId(isNull())
        );
    }

    @Test
    void argumentMatchersTest() {
        // Given
        Exam exam = new Exam(5L, "Math");
        when(examRepository.findAllExams()).thenReturn(Data.EXAM_LIST);
        when(questionRepository.findQuestionsByExamId(anyLong())).thenReturn(Data.QUESTION_LIST);

        // When
        Exam actual = examService.findExamByNameWithQuestions(exam.getName());

        // Then
        assertAll(
                () -> assertNotNull(actual),
                () -> verify(examRepository).findAllExams(),
                () -> verify(questionRepository).findQuestionsByExamId(argThat(argument -> argument != null && argument > 0)),
                () -> verify(questionRepository).findQuestionsByExamId(argThat(argument -> argument != null && argument.equals(5L))),
                () -> verify(questionRepository).findQuestionsByExamId(eq(5L))
        );
    }

    @Test
    void argumentMatchersWithClassTest() {
        // Given
        Exam exam = new Exam(5L, "Math");
        when(examRepository.findAllExams()).thenReturn(Data.EXAM_LIST_WITH_NEGATIVE_IDS);
        when(questionRepository.findQuestionsByExamId(anyLong())).thenReturn(Data.QUESTION_LIST);

        // When
        Exam actual = examService.findExamByNameWithQuestions(exam.getName());

        // Then
        assertAll(
                () -> verify(examRepository).findAllExams(),
                () -> verify(questionRepository).findQuestionsByExamId(argThat(new ArgumentMatcher()))
        );
    }

    @Test
    void argumentCaptorTest() {
        // Given
        Exam exam = new Exam(5L, "Math");
        when(examRepository.findAllExams()).thenReturn(Data.EXAM_LIST);

        // When
        Exam actual = examService.findExamByNameWithQuestions(exam.getName());
        //ArgumentCaptor<Long> argumentCaptor = ArgumentCaptor.forClass(Long.class);

        // Then
        assertAll(
                () -> assertNotNull(actual),
                () -> verify(examRepository).findAllExams(),
                () -> verify(questionRepository).findQuestionsByExamId(argumentCaptor.capture()),
                () -> assertEquals(exam.getId(), argumentCaptor.getValue())
        );
    }

    static class ArgumentMatcher implements org.mockito.ArgumentMatcher<Long> {
        private Long argument;

        @Override
        public boolean matches(Long argument) {
            this.argument = argument;
            return argument != null && argument > 0;
        }

        @Override
        public String toString() {
            return "ArgumentMatcher{" + argument + "} is null or less than 0";
        }
    }

    @Test
    void doThrowTest() {
        // Given
        Exam exam = Data.EXAM;
        exam.setQuestions(Data.QUESTION_LIST);
        doThrow(IllegalArgumentException.class).when(questionRepository).saveQuestionList(anyList());
        assertAll(
                () -> assertThrows(IllegalArgumentException.class, () -> examService.save(Data.EXAM))
        );
    }

    @Test
    void doAnswerTest() {
        when(examRepository.findAllExams()).thenReturn(Data.EXAM_LIST);
        doAnswer(invocation -> {
            Long id = invocation.getArgument(0);
            return id == 5L ? Data.QUESTION_LIST : Collections.emptyList();
        }).when(questionRepository).findQuestionsByExamId(anyLong());

        Exam actual = examService.findExamByNameWithQuestions("Math");

        assertAll(
                () -> assertNotNull(actual),
                () -> assertEquals(5, actual.getQuestions().size()),
                () -> assertTrue(actual.getQuestions().contains("Arithmetic")),
                () -> assertEquals(5L, actual.getId()),
                () -> assertEquals("Math", actual.getName()),
                () -> verify(examRepository).findAllExams(),
                () -> verify(questionRepository).findQuestionsByExamId(anyLong())
        );
    }


    @Test
    void saveExamUsingDoAnswerTest() {
        // Given
        Exam expectedExam = new Exam(8L, "Physics");
        expectedExam.setQuestions(Data.QUESTION_LIST);

        doAnswer(new Answer<Exam>() {
            Long sequence = 8L;
            @Override
            public Exam answer(InvocationOnMock invocationOnMock) throws Throwable {
                Exam exam = invocationOnMock.getArgument(0);
                exam.setId(sequence++);
                return exam;
            }
        }).when(examRepository).save(any(Exam.class));

        // When
        Exam actual = examService.save(expectedExam);

        // Then
        assertAll(
                () -> assertNotNull(actual),
                () -> assertEquals(expectedExam.getId(), actual.getId()),
                () -> assertEquals(expectedExam.getName(), actual.getName()),
                () -> verify(examRepository).save(any(Exam.class)),
                () -> verify(questionRepository).saveQuestionList(anyList())
        );
    }

    @Test
    void doCallRealMethodTest() {
        // Given
        Exam exam = new Exam(5L, "Math");
        when(examRepository.findAllExams()).thenReturn(Data.EXAM_LIST);
        // when(questionRepository.findQuestionsByExamId(anyLong())).thenReturn(Data.QUESTION_LIST);
        doCallRealMethod().when(questionRepository).findQuestionsByExamId(anyLong());

        // When
        Exam actual = examService.findExamByNameWithQuestions(exam.getName());

        // Then
        assertAll(
                () -> assertNotNull(actual),
                () -> assertEquals(5, actual.getQuestions().size()),
                () -> assertTrue(actual.getQuestions().contains("Arithmetic")),
                () -> assertEquals(5L, actual.getId()),
                () -> assertEquals("Math", actual.getName()),
                () -> verify(examRepository).findAllExams(),
                () -> verify(questionRepository).findQuestionsByExamId(anyLong())
        );
    }
}
