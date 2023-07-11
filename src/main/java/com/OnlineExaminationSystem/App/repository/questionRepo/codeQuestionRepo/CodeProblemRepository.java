package com.OnlineExaminationSystem.App.repository.questionRepo.codeQuestionRepo;


import com.OnlineExaminationSystem.App.entity.Exam.questions.StudentAnswer;
import com.OnlineExaminationSystem.App.entity.Exam.questions.codeQuestion.CodeQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CodeProblemRepository extends JpaRepository<CodeQuestion, Long> {

    Optional<List<CodeQuestion>> findAllByExamId(Long examId);

}

