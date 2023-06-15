package com.OnlineExaminationSystem.App.repository;

import com.OnlineExaminationSystem.App.entity.users.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

     Optional<Student> findStudentById(Long studentId);
     Optional<Student> findStudentByUniversityIdAndIdNot(long universityId, long id);

     void deleteById(Long studentId);

}
