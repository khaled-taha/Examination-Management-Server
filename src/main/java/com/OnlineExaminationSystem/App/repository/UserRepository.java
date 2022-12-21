package com.OnlineExaminationSystem.App.repository;

import com.OnlineExaminationSystem.App.entity.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;


import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT i from User i where i.id != Userid and i.email like UserEmail")
    Optional<User> findUserByEmail(@Param("Userid") Long id, @Param("UserEmail") String Email);
}
