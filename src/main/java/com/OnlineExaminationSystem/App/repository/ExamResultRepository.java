package com.OnlineExaminationSystem.App.repository;

import com.OnlineExaminationSystem.App.entity.Exam.ExamAttempt;
import com.OnlineExaminationSystem.App.entity.Exam.ExamResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;

public interface ExamResultRepository extends JpaRepository<ExamResult, Long> {

    ExamResult getExamResultByExamAttemptId(long examAttemptId);

    List<ExamResult> findAllByExamAttemptIn(List<ExamAttempt> examAttempts);
}
