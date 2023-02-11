package com.OnlineExaminationSystem.App.repository;

import com.OnlineExaminationSystem.App.entity.exam.StudentAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentAnswerRepository extends JpaRepository<StudentAnswer, Long> {

}
