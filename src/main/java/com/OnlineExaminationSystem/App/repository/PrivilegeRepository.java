package com.OnlineExaminationSystem.App.repository;

import com.OnlineExaminationSystem.App.entity.users.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PrivilegeRepository extends JpaRepository<Privilege, Integer> {

}
