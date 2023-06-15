package com.OnlineExaminationSystem.App.entity.dto.studentAnswer;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExamResultDto {
    private Long id;
    private double score;
    private boolean passed;

    private String dateTime;

    // User Details
    private String name;
    private String groupName;



}
