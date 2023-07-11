package com.OnlineExaminationSystem.App.entity.dto.exam;

import com.OnlineExaminationSystem.App.entity.Exam.questions.Question;
import com.OnlineExaminationSystem.App.entity.Exam.questions.QuestionType;
import com.OnlineExaminationSystem.App.entity.Exam.questions.codeQuestion.CodeQuestion;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CodeQuestionDto {
    private Long id;
    private String questionText;
    private int points;
    private QuestionType questionType;
    private String header;
    private int timeLimit;

    public static CodeQuestionDto mapToCodeQuestionDto(CodeQuestion codeQuestion){

        return CodeQuestionDto.builder()
                .id(codeQuestion.getId())
                .questionText(codeQuestion.getQuestionText())
                .points(codeQuestion.getPoints())
                .questionType(codeQuestion.getQuestionType())
                .header(codeQuestion.getHeader())
                .timeLimit(codeQuestion.getTimeLimit())
                .build();
    }
}
