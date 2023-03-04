package com.OnlineExaminationSystem.App.entity.Exam;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Question", schema = "public")
@NoArgsConstructor
@Setter
@Getter
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Exam exam;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<QuestionAnswer> questionAnswers = new ArrayList<>();

    // Getters and setters
}