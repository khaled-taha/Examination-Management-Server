package com.OnlineExaminationSystem.App.entity.dto;

import lombok.Builder;

import java.util.List;

@Builder
public class QuestionDto {

    private Long id;
    private String questionText;
    private int points;
    private String questionType;
    private List<QuestionAnswerDto> questionAnswers;
}
