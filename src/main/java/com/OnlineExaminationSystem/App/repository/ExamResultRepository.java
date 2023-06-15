package com.OnlineExaminationSystem.App.repository;

import com.OnlineExaminationSystem.App.entity.Exam.ExamResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ExamResultRepository extends JpaRepository<ExamResult, Long> {

    ExamResult getExamResultByExamAttemptId(long examAttemptId);

    @Query("SELECT er FROM ExamResult er JOIN er.examAttempt ea WHERE ea.exam.id = :examId")
    List<ExamResult> findAllByExamId(@Param("examId") Long examId);
}
