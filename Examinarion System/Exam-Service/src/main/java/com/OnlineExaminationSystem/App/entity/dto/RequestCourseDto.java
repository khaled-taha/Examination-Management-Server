package com.OnlineExaminationSystem.App.entity.dto;

import com.OnlineExaminationSystem.App.entity.Exam.Course;
import com.OnlineExaminationSystem.App.entity.Exam.Group;
import com.OnlineExaminationSystem.App.entity.dto.userDto.AdminDto;
import com.OnlineExaminationSystem.App.entity.users.Admin;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class RequestCourseDto {

    private long id;
    private String name;
    private String code;
    private Group group;
    private Set<Long> adminIds;


}
