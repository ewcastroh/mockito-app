package com.ewch.mockito.app.service;

import com.ewch.mockito.app.model.Exam;
import com.ewch.mockito.app.repository.ExamRepository;
import com.ewch.mockito.app.repository.QuestionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ExamServiceImplTest {

    private ExamService examService;
    private ExamRepository examRepository;
    private QuestionRepository questionRepository;

    @BeforeEach
    void setUp() {
        this.examRepository = mock(ExamRepository.class);
        this.questionRepository = mock(QuestionRepository.class);
        this.examService = new ExamServiceImpl(examRepository, questionRepository);
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
}
