package com.OnlineExaminationSystem.App.service;


import com.OnlineExaminationSystem.App.entity.Exam.Exam;
import com.OnlineExaminationSystem.App.entity.Exam.questions.QuestionType;
import com.OnlineExaminationSystem.App.entity.Exam.questions.codeQuestion.CodeQuestion;
import com.OnlineExaminationSystem.App.entity.Exam.questions.codeQuestion.TestCase;
import com.OnlineExaminationSystem.App.repository.ExamRepository;
import com.OnlineExaminationSystem.App.repository.questionRepo.codeQuestionRepo.CodeProblemRepository;
import com.OnlineExaminationSystem.App.repository.questionRepo.codeQuestionRepo.TestCaseRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CodeQuestionService {

    @Autowired
    private ExamRepository examRepository;

    @Autowired
    private final CodeProblemRepository codeProblemRepository;

    @Autowired
    private final TestCaseRepository testCaseRepository;

    public List<CodeQuestion> saveCodeProblem(List<CodeQuestion> codeQuestions, Long examId) {
        Exam exam = this.examRepository.findExamById(examId).get();
        exam.setCodeProblems(codeQuestions);
        System.out.println("ExamID: " + examId);
        System.out.println(codeQuestions.get(0).getTestCases());

        codeQuestions.forEach(question -> {
            if(question.getQuestionType().name().equalsIgnoreCase(QuestionType.CODING.name())) {
                question.setExam(exam);
                question.getTestCases().
                        forEach(testCase -> testCase.setCodeProblem(question));
            }
        });
        return this.codeProblemRepository.saveAll(codeQuestions);
    }

    public List<CodeQuestion> getCodeProblemByExamId(Long examId) {
        return codeProblemRepository.findAllByExamId(examId).orElse(null);
    }

    public void deleteCodeProblem(Long id) {
        codeProblemRepository.deleteById(id);
    }

    public void deleteTestCases(List<TestCase> testCases){
        this.testCaseRepository.deleteAll(testCases);
    }

}
