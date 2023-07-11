package com.OnlineExaminationSystem.App.repository.questionRepo.codeQuestionRepo;


import com.OnlineExaminationSystem.App.entity.Exam.questions.codeQuestion.TestCase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestCaseRepository extends JpaRepository<TestCase, Long> {
}