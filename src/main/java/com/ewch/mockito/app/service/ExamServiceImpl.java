package com.ewch.mockito.app.service;

import com.ewch.mockito.app.model.Exam;
import com.ewch.mockito.app.repository.ExamRepository;
import com.ewch.mockito.app.repository.QuestionRepository;

import java.util.List;
import java.util.Optional;

public class ExamServiceImpl implements ExamService {

    private final ExamRepository examRepository;
    private final QuestionRepository questionRepository;

    public ExamServiceImpl(ExamRepository examRepository, QuestionRepository questionRepository) {
        this.examRepository = examRepository;
        this.questionRepository = questionRepository;
    }

    @Override
    public Optional<Exam> findExamByName(String name) {
        return examRepository.findAllExams()
                .stream()
                .filter(exam -> exam.getName().equals(name))
                .findFirst();
    }

    @Override
    public Exam findExamByNameWithQuestions(String name) {
        Optional<Exam> examOptional = findExamByName(name);
        examOptional.ifPresent(exam -> {
            List<String> questions = questionRepository.findQuestionsByExamId(exam.getId());
            exam.setQuestions(questions);
        });
        return examOptional.orElse(null);
    }
}
