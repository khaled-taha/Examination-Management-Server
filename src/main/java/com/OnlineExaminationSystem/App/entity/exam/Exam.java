package com.OnlineExaminationSystem.App.entity.exam;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Exam", schema = "examinationsystem")
@NoArgsConstructor
@Setter
@Getter
public class Exam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "examId")
    private Long examId;

    @Column(name = "examName", unique = true)
    private String examName;

    @Column(name = "examDate")
    private Date examDate;

    @Column(name = "duration")
    private Integer duration;

    @Column(name = "startTime")
    private LocalDateTime startTime;

    @Column(name = "endTime")
    private LocalDateTime endTime;


    @OneToMany(mappedBy = "exam", cascade = CascadeType.ALL)
    private List<Question> questions = new ArrayList<>();
    // getters and setters
}