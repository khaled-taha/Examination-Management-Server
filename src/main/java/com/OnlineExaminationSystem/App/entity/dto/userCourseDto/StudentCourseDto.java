package com.OnlineExaminationSystem.App.entity.dto.userCourseDto;

import com.OnlineExaminationSystem.App.entity.Exam.Group;
import com.OnlineExaminationSystem.App.entity.users.Student;
import lombok.Data;

@Data
public class StudentCourseDto {

    private long id;
    private String firstName;
    private String lastName;
    private Long universityId;

    private String email;
    private boolean enable;
    private Group group;

    public StudentCourseDto(long id, String firstName, String lastName, Long universityId, String email, boolean enable, Group group) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.universityId = universityId;
        this.email = email;
        this.enable = enable;
        this.group = group;
    }

    public static StudentCourseDto getStudentDto(Student student){
        return new StudentCourseDto(student.getId(), student.getFirstName(), student.getLastName(),
                student.getUniversityId(), student.getEmail(), student.isEnable(), student.getGroup());
    }
}
