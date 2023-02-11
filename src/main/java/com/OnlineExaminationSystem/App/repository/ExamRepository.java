package com.OnlineExaminationSystem.App.repository;

import com.OnlineExaminationSystem.App.entity.exam.Exam;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ExamRepository extends JpaRepository<Exam, Long> {

    Optional<Exam> findByExamName(String examName);

    Optional<Exam> findExamById(long id);
}