package com.OnlineExaminationSystem.App.entity.Exam.questions;

import com.OnlineExaminationSystem.App.entity.Exam.ExamAttempt;
import com.OnlineExaminationSystem.App.entity.Exam.questions.standardQuestion.StandardQuestionAnswer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "student_answer", schema = "public")
@NoArgsConstructor
@Setter
@Getter
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "studentAnswerType")
public class StudentAnswer {

    @Id
    @SequenceGenerator(name = "student_answer_sequence", sequenceName = "student_answer_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "student_answer_sequence")
    private Long id;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "examAttempt_id")
    @JsonIgnore
    private ExamAttempt examAttempt;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "question_id")
    private Question question;

    @Column(name = "score")
    private double points;


    @JoinColumn(name = "answerCompleteQuestion")
    private String answerCompleteQuestion;


    // Getters and setters


}