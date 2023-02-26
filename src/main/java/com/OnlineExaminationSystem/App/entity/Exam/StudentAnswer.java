package com.OnlineExaminationSystem.App.entity.Exam;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "student_answer", schema = "public")
@NoArgsConstructor
@Setter
@Getter
public class StudentAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "question_answer_id", nullable = false)
    private QuestionAnswer questionAnswer;

    @ManyToOne
    @JoinColumn(name = "examAttempt_id")
    @JsonIgnore
    private ExamAttempt examAttempt;

    @Column(name = "score")
    private double points;

    // Getters and setters
}