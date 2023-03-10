package com.OnlineExaminationSystem.App.entity.dto.exam;


import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class QuestionAnswerDto {

    private Long id;
    private String answerText;
}
