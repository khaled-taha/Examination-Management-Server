package com.OnlineExaminationSystem.App.repository;

import com.OnlineExaminationSystem.App.entity.Exam.StudentAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentAnswerRepository extends JpaRepository<StudentAnswer, Long> {

    List<StudentAnswer> findAllByExamAttemptId(long examAttemptId);
    StudentAnswer findByExamAttemptIdAndQuestionId(Long attemptId, Long questionId);

}
