package com.exam.code.Judgment.services;

import com.exam.code.Judgment.models.CodeProblem;
import com.exam.code.Judgment.repositories.CodeProblemRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CodeProblemService {

    @Autowired
    private final CodeProblemRepository codeProblemRepository;

    public CodeProblem createCodeProblem(CodeProblem codeProblem) {
        return codeProblemRepository.save(codeProblem);
    }


    public List<CodeProblem> getCodeProblemsByExamId(long examId) {
        return codeProblemRepository.findAllByExamId(examId).orElse(null);
    }

    public CodeProblem updateCodeProblem(Long id, CodeProblem codeProblem) {
        CodeProblem existingProblem = codeProblemRepository.findById(id).orElse(null);
        if (existingProblem != null) {
            existingProblem.setHeader(codeProblem.getHeader());
            existingProblem.setDescription(codeProblem.getDescription());
            existingProblem.setTimeLimit(codeProblem.getTimeLimit());
            existingProblem.setMemorySize(codeProblem.getMemorySize());
            existingProblem.setTestCases(codeProblem.getTestCases());
            return codeProblemRepository.save(existingProblem);
        }
        return null;
    }

    public void deleteCodeProblem(Long id) {
        codeProblemRepository.deleteById(id);
    }
}