package com.OnlineExaminationSystem.App.repository;


import com.OnlineExaminationSystem.App.entity.Exam.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Integer> {
}
