package com.OnlineExaminationSystem.App.repository.questionRepo;

import com.OnlineExaminationSystem.App.entity.Exam.questions.standardQuestion.StandardQuestionAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionAnswerRepository extends JpaRepository<StandardQuestionAnswer, Long> {

    List<StandardQuestionAnswer> findAllByQuestionId(long questionId);
    StandardQuestionAnswer findByQuestionId(long questionId);

}
