package com.OnlineExaminationSystem.App.entity.dto;

import com.OnlineExaminationSystem.App.entity.Exam.Course;
import com.OnlineExaminationSystem.App.entity.dto.userCourseDto.AdminCourseDto;
import lombok.Data;

import java.util.List;

@Data
public class CourseDtoResponse {

    private long id;
    private String courseName;
    private String courseCode;
    private String groupName;
    private List<AdminCourseDto> admins;
    private int numOfExams;

    public CourseDtoResponse(long id, String courseName, String courseCode, String groupName, List<AdminCourseDto> admins, int numOfExams) {
        this.id = id;
        this.courseName = courseName;
        this.courseCode = courseCode;
        this.groupName = groupName;
        this.admins = admins;
        this.numOfExams = numOfExams;
    }

    public static CourseDtoResponse getCourseDto(Course course){
        return new CourseDtoResponse(course.getId(), course.getName(), course.getCode()
                , course.getGroup().getName(), AdminCourseDto.getAdmins(course.getAdmins()),
                course.getExams() != null ? course.getExams().size() : 0);
    }

}
