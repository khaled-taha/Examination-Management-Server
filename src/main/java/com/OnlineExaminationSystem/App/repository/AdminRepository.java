package com.OnlineExaminationSystem.App.repository;
import com.OnlineExaminationSystem.App.entity.users.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;


import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {

    Optional<Admin> findAdminById(Long AdminId);
    @Query(value = "SELECT i from Admin i where i.universityId != adminId")
    Optional<Admin> findAdminByUniversityId(Long adminId);

    void deleteById(Long AdminId);



}
