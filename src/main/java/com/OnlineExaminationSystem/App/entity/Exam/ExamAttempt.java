package com.OnlineExaminationSystem.App.entity.Exam;

import com.OnlineExaminationSystem.App.entity.users.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ExamAttempt", schema = "examinationsystem")
@NoArgsConstructor
@Setter
@Getter
public class ExamAttempt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "exam_id")
    private Exam exam;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "startTime")
    @CreationTimestamp
    private LocalDateTime startTime;

    @Column(name = "endTime")
    @CreationTimestamp
    private LocalDateTime endTime;

    // getters and setters
}
