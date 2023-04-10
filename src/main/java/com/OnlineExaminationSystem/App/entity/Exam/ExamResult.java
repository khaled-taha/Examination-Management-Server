package com.OnlineExaminationSystem.App.entity.Exam;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "ExamResult", schema = "public")
@NoArgsConstructor
@Setter
@Getter
public class ExamResult {


    @Id
    @SequenceGenerator(name = "examResult_sequence", sequenceName = "examResult_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "examResult_sequence")
    private Long id;

    @OneToOne
    @JoinColumn(name = "exam_attempt_id", unique = true)
    @JsonIgnore
    private ExamAttempt examAttempt;

    @Column(name = "score")
    private double score;

    @Column(name = "passed")
    private boolean passed;

    // getters and setters
}

