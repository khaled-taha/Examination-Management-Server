package com.OnlineExaminationSystem.App.entity.dto.studentAnswer;

import lombok.Data;

@Data
public class CompleteStudentAnswerDto {
    private long questionId;
    private String textAnswer;

    public CompleteStudentAnswerDto(long questionId, String textAnswer) {
        this.questionId = questionId;
        this.textAnswer = textAnswer;
    }
}
