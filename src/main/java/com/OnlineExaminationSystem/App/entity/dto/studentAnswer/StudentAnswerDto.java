package com.OnlineExaminationSystem.App.entity.dto.studentAnswer;


import com.OnlineExaminationSystem.App.entity.Exam.questions.Question;
import com.OnlineExaminationSystem.App.entity.Exam.questions.StudentAnswer;
import com.OnlineExaminationSystem.App.entity.Exam.questions.codeQuestion.CodeStudentAnswer;
import com.OnlineExaminationSystem.App.entity.Exam.questions.standardQuestion.StandardQuestionAnswer;
import com.OnlineExaminationSystem.App.entity.Exam.questions.standardQuestion.StandardStudentAnswer;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class StudentAnswerDto {

    private List<StandardStudentAnswer> standardQuestionAnswers = new ArrayList<>();
    private List<CodeStudentAnswer> codeStudentAnswers = new ArrayList<>();

    public StudentAnswerDto mapToStudentAnswerDto(List<StudentAnswer> answers){
        answers.stream().forEach(studentAnswer -> {
            if(studentAnswer instanceof StandardStudentAnswer s)
                this.standardQuestionAnswers.add(s);
            else if(studentAnswer instanceof CodeStudentAnswer c)
                this.codeStudentAnswers.add(c);
        });
        return this;
    }

}
