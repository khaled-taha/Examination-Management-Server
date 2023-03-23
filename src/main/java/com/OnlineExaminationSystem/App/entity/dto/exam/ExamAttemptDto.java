package com.OnlineExaminationSystem.App.entity.dto.exam;

import com.OnlineExaminationSystem.App.entity.Exam.ExamAttempt;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;


@Data
@Builder
public class ExamAttemptDto {

    private Long id;
    private String firstName;
    private String lastName;
    private LocalDateTime startTime;
    private LocalDateTime endTime;


    public static ExamAttemptDto mapToExamAttemptDto(ExamAttempt attempt){
        return ExamAttemptDto
                .builder()
                .id(attempt.getId())
                .firstName(attempt.getUser().getFirstName())
                .lastName(attempt.getUser().getLastName())
                .startTime(attempt.getStartTime())
                .endTime(attempt.getEndTime())
                .build();
    }

}
