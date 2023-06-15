package com.OnlineExaminationSystem.App.entity.Exam;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Entity
@Table(name = "question_answer", schema = "public")
@NoArgsConstructor
@Setter
@Getter
@ToString
public class QuestionAnswer {

    @Id
    @SequenceGenerator(name = "question_answer_sequence", sequenceName = "question_answer_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "question_answer_sequence")
    private Long id;

    @Column(name = "answer_text", nullable = false)
    private String answerText;

    @Column(name = "correctAnswer")
    private boolean correctAnswer;

    @Column(name = "comment")
    private String comment;

    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    @JsonIgnore
    private Question question;


}
