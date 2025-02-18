package com.ewch.mockito.app.service;

import com.ewch.mockito.app.model.Exam;

import java.util.List;

public class Data {

    public static final List<Exam> EXAM_LIST = List.of(
            new Exam(5L, "Math"),
            new Exam(6L, "Language")
    );

    public static final List<Exam> EXAM_LIST_WITH_NEGATIVE_IDS = List.of(
            new Exam(-5L, "Math"),
            new Exam(-6L, "Language")
    );

    public static final List<Exam> EXAM_LIST_WITH_NULL_IDS = List.of(
            new Exam(null, "Math"),
            new Exam(null, "Language")
    );

    public static final List<String> QUESTION_LIST = List.of("Arithmetic", "Derivatives", "Integrals", "Trigonometry", "Geometry");

    public static final Exam EXAM = new Exam(null, "Physics");
}
