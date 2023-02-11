package com.OnlineExaminationSystem.App.repository;

import com.OnlineExaminationSystem.App.entity.exam.QuestionAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionAnswerRepository extends JpaRepository<QuestionAnswer, Long> {
}
