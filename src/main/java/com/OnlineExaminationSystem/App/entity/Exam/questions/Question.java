package com.OnlineExaminationSystem.App.entity.Exam.questions;

import com.OnlineExaminationSystem.App.entity.Exam.Exam;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "Question", schema = "public")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "type")
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


    // Getters and setters
}