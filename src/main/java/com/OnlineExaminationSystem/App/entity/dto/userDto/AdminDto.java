package com.OnlineExaminationSystem.App.entity.dto.userDto;

import com.OnlineExaminationSystem.App.entity.users.Admin;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class AdminDto {
    private String firstName;
    private String lastName;
    private String specialization;

    public AdminDto(String firstName, String lastName, String specialization) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.specialization = specialization;
    }

    public static AdminDto getAdminDto(Admin admin){
        return new AdminDto(admin.getFirstName(), admin.getLastName(), admin.getSpecialization());
    }

    public static List<AdminDto> getAdmins(List<Admin> admins){
        List<AdminDto> adminDtos = new ArrayList<>();
        admins.stream().forEach((admin) -> { adminDtos.add(AdminDto.getAdminDto(admin)); });
        return adminDtos;
    }
}
