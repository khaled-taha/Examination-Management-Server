package com.OnlineExaminationSystem.App.entity.dto.exam;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class QuestionDto {

    private Long id;
    private String questionText;
    private int points;
    private String questionType;
    private List<QuestionAnswerDto> questionAnswers;
}
