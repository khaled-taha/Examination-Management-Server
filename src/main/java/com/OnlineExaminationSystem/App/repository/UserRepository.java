package com.OnlineExaminationSystem.App.repository;

import com.OnlineExaminationSystem.App.entity.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;



import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT * from User  where id <> :Userid and email like :UserEmail",
    nativeQuery = true)
    Optional<User> findUserByEmail(@Param("Userid") Long id, @Param("UserEmail") String Email);
}
