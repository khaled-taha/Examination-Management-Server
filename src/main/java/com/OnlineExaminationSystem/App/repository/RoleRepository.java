package com.OnlineExaminationSystem.App.repository;

import com.OnlineExaminationSystem.App.entity.users.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

      Optional<Role> findRoleById(int id);
      Optional<Role> findByRole(String role);
      Optional<Role> deleteById(int id);
}
