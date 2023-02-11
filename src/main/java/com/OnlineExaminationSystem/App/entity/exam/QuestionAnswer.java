package com.OnlineExaminationSystem.App.entity.Exam;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "question_answer")
@NoArgsConstructor
@Setter
@Getter
public class QuestionAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "answer_text", nullable = false)
    private String answerText;

    @Column(name = "correctAnswer")
    private boolean correctAnswer;

    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

}
/*
-- Store a multiple choice question
        INSERT INTO Question (question_text, question_type) VALUES ('What is the capital of France?', 'MULTIPLE_CHOICE');

        INSERT INTO QuestionAnswer (question_id, answer_text, is_correct)
        VALUES (LAST_INSERT_ID(), 'Paris', 1),
        (LAST_INSERT_ID(), 'Berlin', 0),
        (LAST_INSERT_ID(), 'London', 0),
        (LAST_INSERT_ID(), 'Rome', 0);

        -- Store a true/false question
        INSERT INTO Question (question_text, question_type) VALUES ('Is the sun a star?', 'TRUE_FALSE');

        INSERT INTO QuestionAnswer (question_id, answer_text, is_correct)
        VALUES (LAST_INSERT_ID(), 'True', 1),
        (LAST_INSERT_ID(), 'False', 0);

        -- Store a fill in the blank question
        INSERT INTO Question (question_text, question_type) VALUES ('The capital of Italy is ________', 'FILL_IN_THE_BLANK');

        INSERT INTO QuestionAnswer (question_id, answer_text, is_correct)
        VALUES (LAST_INSERT_ID(), 'Rome', 1);
*/