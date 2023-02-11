package com.OnlineExaminationSystem.App.repository;

import com.OnlineExaminationSystem.App.entity.exam.ExamResult;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExamResultRepository extends JpaRepository<ExamResult, Long> {

    ExamResult getExamResultByExamAttemptId(long examAttemptId);
}
