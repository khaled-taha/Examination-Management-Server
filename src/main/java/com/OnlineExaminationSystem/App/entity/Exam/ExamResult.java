package com.OnlineExaminationSystem.App.entity.Exam;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "exam_attempt_id")
    private ExamAttempt examAttempt;

    @Column(name = "score")
    private float score;

    @Column(name = "passed")
    private boolean passed;

    // getters and setters
}

