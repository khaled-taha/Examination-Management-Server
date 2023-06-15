package com.exam.code.Judgment.models;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "test_cases", schema = "public")
@Getter
@Setter
public class TestCase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String input;
    private String expectedOutput;

    @ManyToOne
    private CodeProblem codeProblem;
}