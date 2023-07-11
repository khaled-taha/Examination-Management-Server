package com.OnlineExaminationSystem.App.repository;

import com.OnlineExaminationSystem.App.entity.Exam.ExamAttempt;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExamAttemptRepository extends JpaRepository<ExamAttempt, Long> {

    List<ExamAttempt> getAllExamAttemptByUserId(long userId);

    List<ExamAttempt> getAllExamAttemptByExamId(long examId);

    List<ExamAttempt> getExamAttemptByUserId(long UserId);

}
