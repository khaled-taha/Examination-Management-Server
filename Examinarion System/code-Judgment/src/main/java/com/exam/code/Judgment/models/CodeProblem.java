package com.exam.code.Judgment.models;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "code_problem", schema = "public")
@Setter
@Getter
public class CodeProblem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String header;
    private String description;
    private int timeLimit;
    private long memorySize;
    private long examId;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codeProblem")
    private List<TestCase> testCases;
}