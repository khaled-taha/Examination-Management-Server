package com.OnlineExaminationSystem.App.entity.exam;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Entity
@Table(name = "Question", schema = "examinationsystem")
@NoArgsConstructor
@Setter
@Getter
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "question_text", nullable = false)
    @NotEmpty
    private String questionText;

    @Column(name = "points", nullable = false)
    private int points;

    @Column(name = "question_type", nullable = false)
    private String questionType;

    @ManyToOne
    @JoinColumn(name = "exam_id", nullable = false)
    private Exam exam;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private List<QuestionAnswer> questionAnswers;

    // Getters and setters
}