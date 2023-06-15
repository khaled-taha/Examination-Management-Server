package com.OnlineExaminationSystem.App.entity.Exam;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Question", schema = "public")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class Question {

    @Id
    @SequenceGenerator(name = "Question_sequence", sequenceName = "Question_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Question_sequence")
    private Long id;

    @Column(name = "question_text", nullable = false)
    @NotEmpty
    private String questionText;

    @Column(name = "points", nullable = false)
    private int points;

    @Column(name = "question_type", nullable = false)
    @Enumerated
    private QuestionType questionType;

    @ManyToOne
    @JoinColumn(name = "exam_id", nullable = false)
    @JsonIgnore
    private Exam exam;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private List<QuestionAnswer> questionAnswers = new ArrayList<>();

    // Getters and setters
}