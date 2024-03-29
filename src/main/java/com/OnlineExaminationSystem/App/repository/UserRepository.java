package com.OnlineExaminationSystem.App.repository;

import com.OnlineExaminationSystem.App.entity.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByEmailAndIdNot(String email, Long id);

    Optional<User> findUserByEmail(String email);
}
