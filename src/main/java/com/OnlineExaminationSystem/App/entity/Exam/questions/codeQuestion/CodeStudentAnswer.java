package com.OnlineExaminationSystem.App.entity.Exam.questions.codeQuestion;


import com.OnlineExaminationSystem.App.entity.Exam.questions.StudentAnswer;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "code_student_answer")
@NoArgsConstructor
@Setter
@Getter
@DiscriminatorValue("CodeStudentAnswer")
public class CodeStudentAnswer extends StudentAnswer {

    @Column(name = "submission")
    private String submission;

    @Column(name = "passedTestCases")
    private int passedTestCases;

    @Column(name = "accepted")
    private short status;

    @Column(name = "code")
    private String code;
}