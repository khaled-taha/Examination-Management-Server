package com.OnlineExaminationSystem.App.service;

import com.OnlineExaminationSystem.App.entity.dto.user.admin.AdminDto;
import com.OnlineExaminationSystem.App.entity.users.Admin;
import com.OnlineExaminationSystem.App.exceptions.ApiException;
import com.OnlineExaminationSystem.App.repository.AdminRepository;
import com.OnlineExaminationSystem.App.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@AllArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;
    private final UserRepository userRepository;


   public AdminDto saveAdmin(AdminDto admin) {

            if(this.userRepository.findUserByEmailAndIdNot(admin.getEmail(), admin.getId()).isPresent()) {
                throw new ApiException("Duplicate Email");
            }
            else if(this.adminRepository.findAdminByUniversityIdAndIdNot(admin.getUniversityId(), admin.getId()).isPresent())
                throw new ApiException("Duplicate UniversityId");

            Admin savedAdmin = new Admin();
            savedAdmin.setId(admin.getId());
            savedAdmin.setFirstName(admin.getFirstName());
            savedAdmin.setLastName(admin.getLastName());
            savedAdmin.setEmail(admin.getEmail());
            savedAdmin.setPassword(admin.getPassword());
            savedAdmin.setSpecialization(admin.getSpecialization());
            savedAdmin.setUniversityId(admin.getUniversityId());
            savedAdmin.setRoles(admin.getRoles());
            savedAdmin.setEnable(admin.isEnable());
            savedAdmin.setLocked(admin.isLocked());

        this.adminRepository.save(savedAdmin);
        return admin;
    }

    public void deleteById(Long adminId) {
        this.adminRepository.findAdminById(adminId)
                .orElseThrow(() -> new ApiException("Admin not found"));
        this.adminRepository.deleteById(adminId);
    }

    public List<AdminDto> getAllAdmins() {

       List<Admin> admins = this.adminRepository.findAll();
       List<AdminDto> savedAdmins = new ArrayList<>();

       admins.stream().forEach(admin -> {
           AdminDto savedAdmin = new AdminDto();
           savedAdmin.setId(admin.getId());
           savedAdmin.setFirstName(admin.getFirstName());
           savedAdmin.setLastName(admin.getLastName());
           savedAdmin.setEmail(admin.getEmail());
           savedAdmin.setPassword(admin.getPassword());
           savedAdmin.setSpecialization(admin.getSpecialization());
           savedAdmin.setUniversityId(admin.getUniversityId());
           savedAdmin.setRoles(admin.getRoles());
           savedAdmin.setEnable(admin.isEnable());
           savedAdmin.setLocked(admin.isLocked());

           savedAdmins.add(savedAdmin);
       });

        System.out.println(savedAdmins);

       return savedAdmins;
    }

    public AdminDto getAdminById(Long adminId) {
        Admin admin = this.adminRepository.findAdminById(adminId)
                .orElseThrow(() -> new ApiException("Admin not found"));

        AdminDto savedAdmin = new AdminDto();
        savedAdmin.setId(admin.getId());
        savedAdmin.setFirstName(admin.getFirstName());
        savedAdmin.setLastName(admin.getLastName());
        savedAdmin.setEmail(admin.getEmail());
        savedAdmin.setPassword(admin.getPassword());
        savedAdmin.setSpecialization(admin.getSpecialization());
        savedAdmin.setUniversityId(admin.getUniversityId());
        savedAdmin.setRoles(admin.getRoles());
        savedAdmin.setEnable(admin.isEnable());
        savedAdmin.setLocked(admin.isLocked());

        return savedAdmin;
    }

}



