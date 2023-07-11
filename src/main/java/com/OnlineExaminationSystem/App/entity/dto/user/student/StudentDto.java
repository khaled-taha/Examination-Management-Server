package com.OnlineExaminationSystem.App.entity.dto.user.student;

import com.OnlineExaminationSystem.App.entity.Exam.Group;
import com.OnlineExaminationSystem.App.entity.users.Role;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class StudentDto {

    private long id;
    private String firstName;
    private String lastName;
    private Long universityId;
    private String email;
    private String password;
    private Set<Role> roles = new HashSet<>();
    private Group group;
    private boolean locked = false;
    private boolean enable = true;
}
