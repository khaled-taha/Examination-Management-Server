package com.OnlineExaminationSystem.App.entity.dto;

import com.OnlineExaminationSystem.App.entity.Exam.Course;
import com.OnlineExaminationSystem.App.entity.Exam.Group;
import com.OnlineExaminationSystem.App.entity.dto.userDto.AdminDto;
import com.OnlineExaminationSystem.App.entity.users.Admin;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class ResponseCourseDto {

    private long id;
    private String courseName;
    private String courseCode;
    private String groupName;
    private List<AdminDto> admins;

    public ResponseCourseDto(String courseName, String courseCode, String groupName, List<AdminDto> admins) {
        this.courseName = courseName;
        this.courseCode = courseCode;
        this.groupName = groupName;
        this.admins = admins;
    }

    public static ResponseCourseDto getCourseDto(Course course, List<Admin> admins){
        return new ResponseCourseDto(course.getName(), course.getCode()
                , course.getGroup().getName(), AdminDto.getAdmins(admins));
    }

}
