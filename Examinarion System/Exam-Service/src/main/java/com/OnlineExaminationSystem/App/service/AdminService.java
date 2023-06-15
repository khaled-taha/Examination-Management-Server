package com.OnlineExaminationSystem.App.service;

import com.OnlineExaminationSystem.App.entity.users.Admin;
import com.OnlineExaminationSystem.App.exceptions.ApiException;
import com.OnlineExaminationSystem.App.repository.AdminRepository;
import com.OnlineExaminationSystem.App.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;
    private final UserRepository userRepository;


   public Admin saveAdmin(Admin admin) {

            if(this.userRepository.findUserByEmailAndIdNot(admin.getEmail(), admin.getId()).isPresent()) {
                throw new ApiException("Duplicate Email");
            }
            else if(this.adminRepository.findAdminByUniversityIdAndIdNot(admin.getUniversityId(), admin.getId()).isPresent())
                throw new ApiException("Duplicate UniversityId");

        this.adminRepository.save(admin);
        return admin;
    }

    public void deleteById(Long adminId) {
        Admin admin = this.adminRepository.findAdminById(adminId)
                .orElseThrow(() -> new ApiException("Admin not found"));
        this.adminRepository.deleteById(adminId);
    }

    public List<Admin> getAllAdmins() {
        return this.adminRepository.findAll();
    }

    public Admin getAdminById(Long adminId) {
        Admin admin = this.adminRepository.findAdminById(adminId)
                .orElseThrow(() -> new ApiException("Admin not found"));

        return admin;
    }

}



