package com.OnlineExaminationSystem.App.entity.Exam;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "ExamResult", schema = "public")
@NoArgsConstructor
@Setter
@Getter
public class ExamResult {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    @JoinColumn(name = "exam_attempt_id")
    @JsonIgnore
    private ExamAttempt examAttempt;

    @Column(name = "score")
    private double score;

    @Column(name = "passed")
    private boolean passed;

    // getters and setters
}

