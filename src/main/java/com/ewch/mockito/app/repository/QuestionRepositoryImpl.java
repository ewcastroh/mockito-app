package com.ewch.mockito.app.repository;

import com.ewch.mockito.app.utils.Data;

import java.util.List;

public class QuestionRepositoryImpl implements QuestionRepository {

    @Override
    public List<String> findQuestionsByExamId(Long examId) {
        System.out.println("QuestionRepositoryImpl.findQuestionsByExamId");
        System.out.println("Finding questions by exam id: " + examId);
        System.out.println("Returning questions: " + Data.QUESTION_LIST);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Data.QUESTION_LIST;
    }

    @Override
    public void saveQuestionList(List<String> questions) {
        System.out.println("QuestionRepositoryImpl.saveQuestionList");
        System.out.println("Saving questions: " + questions);
    }
}
