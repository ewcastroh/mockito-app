package com.ewch.mockito.app.repository;

import com.ewch.mockito.app.model.Exam;

import java.util.List;

public interface ExamRepository {
    List<Exam> findAllExams();
}
