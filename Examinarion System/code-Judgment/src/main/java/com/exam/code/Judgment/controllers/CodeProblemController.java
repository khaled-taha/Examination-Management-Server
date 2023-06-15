package com.exam.code.Judgment.controllers;

import com.exam.code.Judgment.models.CodeProblem;
import com.exam.code.Judgment.services.CodeProblemService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/codeproblems")
public class CodeProblemController {
    @Autowired
    private final CodeProblemService codeProblemService;

    @PostMapping
    public ResponseEntity<CodeProblem> createCodeProblem(@RequestBody CodeProblem codeProblem) {
        CodeProblem createdProblem = codeProblemService.createCodeProblem(codeProblem);
        return new ResponseEntity<>(createdProblem, HttpStatus.CREATED);
    }

    @GetMapping("/{examId}")
    public ResponseEntity<List<CodeProblem>> getCodeProblemById(@PathVariable("examId") long examId) {
        List<CodeProblem> codeProblems = codeProblemService.getCodeProblemsByExamId(examId);
        if (codeProblems != null) {
            return new ResponseEntity<>(codeProblems, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CodeProblem> updateCodeProblem(
            @PathVariable("id") Long id, @RequestBody CodeProblem codeProblem) {
        CodeProblem updatedProblem = codeProblemService.updateCodeProblem(id, codeProblem);
        if (updatedProblem != null) {
            return new ResponseEntity<>(updatedProblem, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCodeProblem(@PathVariable("id") Long id) {
        codeProblemService.deleteCodeProblem(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}