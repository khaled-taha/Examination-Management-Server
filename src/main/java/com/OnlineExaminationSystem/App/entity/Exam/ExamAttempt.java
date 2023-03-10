package com.OnlineExaminationSystem.App.entity.Exam;

import com.OnlineExaminationSystem.App.entity.users.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name = "ExamAttempt", schema = "public")
@NoArgsConstructor
@Setter
@Getter
public class ExamAttempt {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "exam_id")
    @JsonIgnore
    private Exam exam;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @Column(name = "startTime")
    @CreationTimestamp
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm a")
    private LocalDateTime startTime;

    @Column(name = "endTime")
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm a")
    private LocalDateTime endTime;

    // getters and setters
}