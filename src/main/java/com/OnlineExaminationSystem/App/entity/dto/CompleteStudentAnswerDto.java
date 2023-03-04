package com.OnlineExaminationSystem.App.entity.dto;

import lombok.Data;

@Data
public class CompleteStudentAnswerDto {
    private long questionId;
    String textAnswer;

    public CompleteStudentAnswerDto(long questionId, String textAnswer) {
        this.questionId = questionId;
        this.textAnswer = textAnswer;
    }
}
