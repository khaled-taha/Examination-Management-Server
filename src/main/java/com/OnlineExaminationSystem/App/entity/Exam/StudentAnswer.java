package com.OnlineExaminationSystem.App.entity.Exam;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "student_answer", schema = "public")
@NoArgsConstructor
@Setter
@Getter
public class StudentAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToMany
    @JoinTable(name = "student_answer_question_answer",
            joinColumns = @JoinColumn(name = "student_answer_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "question_answer_id", referencedColumnName = "id"))
    private List<QuestionAnswer> questionAnswers;

    @ManyToOne
    @JoinColumn(name = "examAttempt_id")
    @JsonIgnore
    private ExamAttempt examAttempt;

    @Column(name = "score")
    private double points;

    public StudentAnswer(List<QuestionAnswer> questionAnswers, ExamAttempt examAttempt, double points) {
        this.questionAnswers = questionAnswers;
        this.examAttempt = examAttempt;
        this.points = points;
    }

    // Getters and setters
}