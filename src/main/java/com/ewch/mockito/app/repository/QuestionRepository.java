package com.ewch.mockito.app.repository;

import java.util.List;

public interface QuestionRepository {
    List<String> findQuestionsByExamId(Long examId);

    void saveQuestionList(List<String> questions);
}
