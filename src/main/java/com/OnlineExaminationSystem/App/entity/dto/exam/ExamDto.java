package com.OnlineExaminationSystem.App.entity.dto.exam;

import com.OnlineExaminationSystem.App.entity.Exam.Course;
import com.OnlineExaminationSystem.App.entity.Exam.Exam;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Builder
@Data
public class ExamDto {

    private Long id;
    private String examName;
    private Integer duration;
    private String startTime;
    private String endTime;
    private int points;
    private double successRate;
    private Course course;
    private List<StandardQuestionDto> questions;
    private List<CodeQuestionDto> codeQuestionDtos;
    private byte questionsPerPage = 1;
    private boolean showResult = false;
    private boolean noCheatingApp;

    public static ExamDto mapToExamDto(Exam exam,
                                       List<StandardQuestionDto> questionsDto,
                                       List<CodeQuestionDto> codeQuestionDtos){

        return ExamDto
                .builder()
                .id(exam.getId())
                .examName(exam.getExamName())
                .duration(exam.getDuration())
                .points(questionsDto.stream().mapToInt(StandardQuestionDto::getPoints).sum())
                .successRate(exam.getSuccessRate())
                .startTime(exam.getStartTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm a")))
                .endTime(exam.getEndTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm a")))
                .questions(questionsDto)
                .codeQuestionDtos(codeQuestionDtos)
                .course(exam.getCourse())
                .questionsPerPage(exam.getQuestionsPerPage())
                .showResult(exam.isShowResult())
                .noCheatingApp(exam.isNoCheatingApp())
                .build();
    }

}
