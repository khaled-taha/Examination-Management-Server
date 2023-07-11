package com.OnlineExaminationSystem.App.entity.dto.studentAnswer;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class ExamResultDto {
    private Long id;
    private Long attemptId;
    private double score;
    private boolean passed;
    private String dateTime;

    // User Details
    private String name;
    private String groupName;


}
