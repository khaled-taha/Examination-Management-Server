package com.OnlineExaminationSystem.App.entity.Exam;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "student_answer", schema = "public")
@NoArgsConstructor
@Setter
@Getter
public class StudentAnswer {

    @Id
    @SequenceGenerator(name = "student_answer_sequence", sequenceName = "student_answer_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "student_answer_sequence")
    private Long id;

    @ManyToMany
    @JoinTable(name = "student_answer_question_answer",
            joinColumns = @JoinColumn(name = "student_answer_id"),
            inverseJoinColumns = @JoinColumn(name = "question_answer_id"))
    private List<QuestionAnswer> questionAnswers;

    @ManyToOne
    @JoinColumn(name = "examAttempt_id")
    @JsonIgnore
    private ExamAttempt examAttempt;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    @Column(name = "score")
    private double points;

    public StudentAnswer(Long id, List<QuestionAnswer> questionAnswers, ExamAttempt examAttempt, Question question, double points) {
        this.id = id;
        this.questionAnswers = questionAnswers;
        this.examAttempt = examAttempt;
        this.points = points;
        this.question = question;
    }

    // Getters and setters

}