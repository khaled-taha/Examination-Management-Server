package com.OnlineExaminationSystem.App.entity.Exam;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "question_answer", schema = "public")
@NoArgsConstructor
@Setter
@Getter
public class QuestionAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "answer_text", nullable = false)
    private String answerText;

    @Column(name = "correctAnswer")
    private boolean correctAnswer;

    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Question question;

}
