package com.OnlineExaminationSystem.App.repository;

import com.OnlineExaminationSystem.App.entity.Exam.Exam;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ExamRepository extends JpaRepository<Exam, Long> {

    List<Exam> findAllExamsByCourseId(Long courseId);

    Optional<Exam> findExamById(long id);
}