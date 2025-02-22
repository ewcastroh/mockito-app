package com.ewch.mockito.app.service;

import com.ewch.mockito.app.model.Exam;
import com.ewch.mockito.app.repository.ExamRepositoryImpl;
import com.ewch.mockito.app.repository.QuestionRepositoryImpl;
import com.ewch.mockito.app.utils.Data;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ExamServiceImplSpyTest {

    @InjectMocks
    private ExamServiceImpl examService;

    @Spy
    private ExamRepositoryImpl examRepository;

    @Spy
    private QuestionRepositoryImpl questionRepository;

    @Test
    void spyTest() {
        ExamService examService = new ExamServiceImpl(examRepository, questionRepository);

        //when(questionRepository.findQuestionsByExamId(anyLong())).thenReturn(Data.QUESTION_LIST);
        // when(examRepository.findAllExams()).thenReturn(Data.EXAM_LIST);
        doReturn(Data.QUESTION_LIST).when(questionRepository).findQuestionsByExamId(anyLong());

        Exam exam = examService.findExamByNameWithQuestions("Math");

        assertAll(
                () -> assertNotNull(exam),
                () -> assertEquals(5L, exam.getId()),
                () -> assertEquals("Math", exam.getName()),
                () -> assertEquals(5, exam.getQuestions().size()),
                () -> assertTrue(exam.getQuestions().contains("Arithmetic")),

                () -> verify(examRepository).findAllExams(),
                () -> verify(questionRepository).findQuestionsByExamId(anyLong())
        );
    }
}
