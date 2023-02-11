package com.OnlineExaminationSystem.App.repository;

import com.OnlineExaminationSystem.App.entity.exam.ExamAttempt;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ExamAttemptRepository extends JpaRepository<ExamAttempt, Long> {

    List<ExamAttempt> getAllExamAttemptByUserId(long userId);
}
