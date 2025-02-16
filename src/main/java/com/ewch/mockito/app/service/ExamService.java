package com.ewch.mockito.app.service;

import com.ewch.mockito.app.model.Exam;

public interface ExamService {
    Exam findExamByName(String name);
}
