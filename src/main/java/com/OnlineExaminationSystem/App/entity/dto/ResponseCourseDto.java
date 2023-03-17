package com.OnlineExaminationSystem.App.entity.dto;

import com.OnlineExaminationSystem.App.entity.Exam.Group;
import lombok.Data;

import java.util.Set;

@Data
public class ResponseCourseDto {

    private long id;
    private String name;
    private String code;
    private Group group;
    private Set<Long> adminIds;

}
