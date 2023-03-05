package com.OnlineExaminationSystem.App.entity.dto;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public class ExamDto {

    private Long id;
    private String examName;
    private Integer duration;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private double successRate;
    private List<QuestionDto> questions;

}
