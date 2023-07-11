package com.OnlineExaminationSystem.App.entity.Exam.questions.codeQuestion;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "test_cases", schema = "public")
@NoArgsConstructor
@Setter
@Getter
public class TestCase {

    @Id
    @SequenceGenerator(name = "test_cases_sequence", sequenceName = "test_cases_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "test_cases_sequence")
    @Column(name = "id")
    private Long id;

    @Column(name = "input")
    private String input;

    @Column(name = "expectedOutput")
    private String expectedOutput;

    @Column(name = "points")
    private int points;

    @ManyToOne
    @JoinColumn(name = "code_problem_id")
    @JsonIgnore
    private CodeQuestion codeProblem;

    public TestCase(Long id, String input, String expectedOutput) {
        this.id = id;
        this.input = input;
        this.expectedOutput = expectedOutput;
    }
}