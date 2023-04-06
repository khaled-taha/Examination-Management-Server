package com.OnlineExaminationSystem.App.entity.dto.exam;

import com.OnlineExaminationSystem.App.entity.Exam.Course;
import com.OnlineExaminationSystem.App.entity.Exam.Exam;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Data
public class ExamDto {

    private Long id;
    private String examName;
    private Integer duration;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int points;
    private double successRate;
    private Course course;
    private List<QuestionDto> questions;

    public static ExamDto mapToExamDto(Exam exam, List<QuestionDto> questionsDto){

        return ExamDto
                .builder()
                .id(exam.getId())
                .examName(exam.getExamName())
                .duration(exam.getDuration())
                .points(questionsDto.stream().mapToInt(QuestionDto::getPoints).sum())
                .successRate(exam.getSuccessRate())
                .startTime(exam.getStartTime())
                .endTime(exam.getEndTime())
                .questions(questionsDto)
                .course(exam.getCourse())
                .build();
    }

}
