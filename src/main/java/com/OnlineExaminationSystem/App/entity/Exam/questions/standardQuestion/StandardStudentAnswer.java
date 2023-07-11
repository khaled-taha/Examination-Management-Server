package com.OnlineExaminationSystem.App.entity.Exam.questions.standardQuestion;


import com.OnlineExaminationSystem.App.entity.Exam.ExamAttempt;
import com.OnlineExaminationSystem.App.entity.Exam.questions.Question;
import com.OnlineExaminationSystem.App.entity.Exam.questions.StudentAnswer;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "standard_student_answer")
@NoArgsConstructor
@Setter
@Getter
@DiscriminatorValue("StandardStudentAnswer")
public class StandardStudentAnswer extends StudentAnswer {

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "student_answer_question_answer",
            joinColumns = @JoinColumn(name = "student_answer_id"),
            inverseJoinColumns = @JoinColumn(name = "standard_answer_id"))
    private List<StandardQuestionAnswer> questionAnswers;

    public StandardStudentAnswer(Long id, List<StandardQuestionAnswer> questionAnswers,
                                 ExamAttempt examAttempt, Question question, double points, String answerCompleteQuestion) {
        super.setId(id);
        super.setExamAttempt(examAttempt);
        super.setPoints(points);
        super.setQuestion(question);
        this.setAnswerCompleteQuestion(answerCompleteQuestion);
        this.questionAnswers = questionAnswers;

    }


}
