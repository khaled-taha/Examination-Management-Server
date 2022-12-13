package com.OnlineExaminationSystem.App.repository;
import com.OnlineExaminationSystem.App.entity.users.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {

    Optional<Admin> findAdminById(Long AdminId);
    Optional<Admin> findAdminByUniversityId(Long AdminId);

    void deleteById(Long AdminId);



}
