package com.OnlineExaminationSystem.App.entity.dto.exam;

import com.OnlineExaminationSystem.App.entity.Exam.questions.Question;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class StandardQuestionDto {

    private Long id;
    private String questionText;
    private int points;
    private String questionType;
    private List<QuestionAnswerDto> questionAnswers;
    public static StandardQuestionDto mapToQuestionDto(Question question,
                                                       List<QuestionAnswerDto> questionAnswerDto){

        return StandardQuestionDto
                .builder()
                .id(question.getId())
                .questionType(question.getQuestionType().name())
                .questionText(question.getQuestionText())
                .points(question.getPoints())
                .questionAnswers(questionAnswerDto)
                .build();
    }


}
