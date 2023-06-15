package com.OnlineExaminationSystem.App.repository;


import com.OnlineExaminationSystem.App.entity.Exam.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Integer> {
    @Transactional
    void deleteAllByExamId(long examId);

    List<Question> findAllByExamId(long examId);
}
