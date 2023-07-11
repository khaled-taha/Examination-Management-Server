package com.OnlineExaminationSystem.App.repository.questionRepo.standardQuestionRepo;


import com.OnlineExaminationSystem.App.entity.Exam.questions.Question;
import com.OnlineExaminationSystem.App.entity.Exam.questions.standardQuestion.StandardQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface StandardQuestionRepository extends JpaRepository<StandardQuestion, Integer> {
    @Transactional
    void deleteAllByExamId(long examId);
    List<StandardQuestion> findAllByExamId(long examId);
}
