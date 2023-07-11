package com.OnlineExaminationSystem.App.entity.Exam;

import com.OnlineExaminationSystem.App.entity.users.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Entity
@Table(name = "ExamAttempt", schema = "public")
@NoArgsConstructor
@Setter
@Getter
public class ExamAttempt {


    @Id
    @SequenceGenerator(name = "examAttempt_sequence", sequenceName = "examAttempt_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "examAttempt_sequence")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "exam_id")
    @JsonIgnore
    private Exam exam;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
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
