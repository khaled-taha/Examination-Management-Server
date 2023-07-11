package com.OnlineExaminationSystem.App.entity.dto.userCourseDto;

import com.OnlineExaminationSystem.App.entity.users.Admin;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class AdminCourseDto {

    private long id;
    private String firstName;
    private String lastName;
    private String specialization;

    public AdminCourseDto(long id, String firstName, String lastName, String specialization) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.specialization = specialization;
    }

    public static AdminCourseDto getAdminDto(Admin admin){
        return new AdminCourseDto(admin.getId(), admin.getFirstName(), admin.getLastName(), admin.getSpecialization());
    }

    public static List<AdminCourseDto> getAdmins(List<Admin> admins){
        List<AdminCourseDto> adminDtos = admins.stream()
                .map(admin -> AdminCourseDto.getAdminDto(admin))
                .distinct()
                .collect(Collectors.toList());
        return adminDtos;
    }
}
