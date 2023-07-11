package com.OnlineExaminationSystem.App.entity.Exam;

import com.OnlineExaminationSystem.App.entity.Exam.questions.codeQuestion.CodeQuestion;
import com.OnlineExaminationSystem.App.entity.Exam.questions.standardQuestion.StandardQuestion;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Exam", schema = "public")
@NoArgsConstructor
@Setter
@Getter
@ToString
public class Exam {

    @Id
    @SequenceGenerator(name = "exam_sequence", sequenceName = "exam_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "exam_sequence")
    @Column(name = "id")
    private Long id;

    @Column(name = "examName", unique = true, nullable = false)
    private String examName;

    @Column(name = "duration")
    private Integer duration;

    @Column(name = "startTime")
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm a")
    private LocalDateTime startTime;

    @Column(name = "endTime")
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm a")
    private LocalDateTime endTime;

    @Column(name = "successRate")
    private double successRate;

    @Column(name = "state")
    private boolean state;

    @Column(name = "questionsPerPage")
    private byte questionsPerPage = 1;

    @Column(name = "showResult")
    private boolean showResult;

    @Column(name = "noCheatingApp")
    private boolean noCheatingApp;

    @OneToMany(mappedBy = "exam")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private List<StandardQuestion> standardQuestions = new ArrayList<>();

    @OneToMany(mappedBy = "exam")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private List<CodeQuestion> codeProblems = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;
    // getters and setters
}