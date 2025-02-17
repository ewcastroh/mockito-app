package com.ewch.mockito.app.service;

import com.ewch.mockito.app.model.Exam;
import com.ewch.mockito.app.repository.ExamRepository;
import com.ewch.mockito.app.repository.QuestionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExamServiceImplTest {

    @InjectMocks
    private ExamServiceImpl examService;

    @Mock
    private ExamRepository examRepository;

    @Mock
    private QuestionRepository questionRepository;

    @BeforeEach
    void setUp() {
        //MockitoAnnotations.openMocks(this);
        /*this.examRepository = mock(ExamRepository.class);
        this.questionRepository = mock(QuestionRepository.class);
        this.examService = new ExamServiceImpl(examRepository, questionRepository);*/
    }

    @Test
    void findExamByNameTest() {
        Exam expectedExam = new Exam(5L, "Math");

        when(examRepository.findAllExams()).thenReturn(Data.EXAM_LIST);

        Optional<Exam> actual = examService.findExamByName("Math");

        assertAll(
                () -> assertTrue(actual.isPresent()),
                () -> assertEquals(expectedExam.getId(), actual.orElseThrow().getId()),
                () -> assertEquals(expectedExam.getName(), actual.orElseThrow().getName())
        );
    }

    @Test
    void findExamByNameWithEmptyListTest() {
        List<Exam> examList = Collections.emptyList();

        when(examRepository.findAllExams()).thenReturn(examList);

        Optional<Exam> actual = examService.findExamByName("Math");

        assertAll(
                () -> assertFalse(actual.isPresent()),
                () -> assertTrue(examRepository.findAllExams().isEmpty())

        );
    }

    @Test
    void findExamByNameWithQuestionsTest() {
        Exam exam = new Exam(5L, "Math");
        int expectedQuestionsCount = 5;
        String expectedQuestion = "Arithmetic";
        when(examRepository.findAllExams()).thenReturn(Data.EXAM_LIST);
        when(questionRepository.findQuestionsByExamId(anyLong())).thenReturn(Data.QUESTION_LIST);

        Exam actual = examService.findExamByNameWithQuestions(exam.getName());

        assertAll(
                () -> assertEquals(expectedQuestionsCount, actual.getQuestions().size()),
                () -> assertTrue(actual.getQuestions().contains(expectedQuestion))
        );
    }

    @Test
    void findExamByNameWithQuestionsVerifyTest() {
        Exam exam = new Exam(5L, "Math");
        int expectedQuestionsCount = 5;
        String expectedQuestion = "Arithmetic";
        when(examRepository.findAllExams()).thenReturn(Data.EXAM_LIST);
        when(questionRepository.findQuestionsByExamId(anyLong())).thenReturn(Data.QUESTION_LIST);

        Exam actual = examService.findExamByNameWithQuestions(exam.getName());

        assertAll(
                () -> assertEquals(expectedQuestionsCount, actual.getQuestions().size()),
                () -> assertTrue(actual.getQuestions().contains(expectedQuestion)),
                () -> verify(examRepository).findAllExams(),
                () -> verify(questionRepository).findQuestionsByExamId(anyLong())
        );
    }

    @Test
    void findExamByNameWithQuestionsListEmptyVerifyTest() {
        Exam exam = new Exam(5L, "Math2");
        when(examRepository.findAllExams()).thenReturn(Data.EXAM_LIST);
        when(questionRepository.findQuestionsByExamId(anyLong())).thenReturn(Data.QUESTION_LIST);

        Exam actual = examService.findExamByNameWithQuestions(exam.getName());

        assertAll(
                () -> assertNull(actual),
                () -> verify(examRepository).findAllExams(),
                () -> verify(questionRepository).findQuestionsByExamId(anyLong())
        );
    }
}
