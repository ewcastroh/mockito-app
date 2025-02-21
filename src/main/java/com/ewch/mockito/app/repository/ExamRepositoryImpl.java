package com.ewch.mockito.app.repository;

import com.ewch.mockito.app.model.Exam;
import com.ewch.mockito.app.utils.Data;

import java.util.List;

public class ExamRepositoryImpl implements ExamRepository {

    @Override
    public Exam save(Exam exam) {
        System.out.println("ExamRepositoryImpl.save");
        System.out.println("Saving exam: " + exam);
        return Data.EXAM;
    }

    @Override
    public List<Exam> findAllExams() {
        System.out.println("ExamRepositoryImpl.findAllExams");
        System.out.println("Finding all exams.");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Data.EXAM_LIST;
    }
}
