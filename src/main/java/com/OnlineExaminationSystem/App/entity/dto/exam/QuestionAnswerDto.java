package com.OnlineExaminationSystem.App.entity.dto.exam;


import com.OnlineExaminationSystem.App.entity.Exam.questions.standardQuestion.StandardQuestionAnswer;
import com.OnlineExaminationSystem.App.entity.Exam.questions.QuestionType;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class QuestionAnswerDto {

    private Long id;
    private String answerText;

    public static QuestionAnswerDto mapToQuestionAnswerDto(StandardQuestionAnswer questionAnswer){

        return QuestionAnswerDto
                .builder()
                .id(questionAnswer.getId())
                .answerText((questionAnswer.getQuestion().getQuestionType() != QuestionType.Matching)
                        ? questionAnswer.getAnswerText() : null)
                .build();
    }
}
