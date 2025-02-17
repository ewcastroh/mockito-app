package com.ewch.mockito.app.service;

import com.ewch.mockito.app.model.Exam;

import java.util.Optional;

public interface ExamService {
    Optional<Exam> findExamByName(String name);

    Exam findExamByNameWithQuestions(String name);

    Exam save(Exam exam);
}
