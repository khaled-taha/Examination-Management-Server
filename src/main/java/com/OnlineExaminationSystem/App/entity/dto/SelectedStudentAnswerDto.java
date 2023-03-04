package com.OnlineExaminationSystem.App.entity.dto;

import lombok.Data;

import java.util.List;

@Data
public class SelectedStudentAnswerDto {

    private long questionId;
    private List<Long> answersId;
}
