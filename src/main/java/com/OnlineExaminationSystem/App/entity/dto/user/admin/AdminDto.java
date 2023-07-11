package com.OnlineExaminationSystem.App.entity.dto.user.admin;

import com.OnlineExaminationSystem.App.entity.users.Role;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class AdminDto {
    private long id;
    private String firstName;
    private String lastName;
    private Long universityId;
    private String email;
    private String password;
    private Set<Role> roles = new HashSet<>();
    private String specialization;
    private boolean locked = false;
    private boolean enable = true;
}
