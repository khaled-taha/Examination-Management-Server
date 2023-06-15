package com.exam.code.Judgment.controllers;

import com.exam.code.Judgment.models.TestCase;
import com.exam.code.Judgment.services.CodeJudgmentService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/codejudgment")
@AllArgsConstructor
public class CodeJudgmentController {
    @Autowired
    private final CodeJudgmentService codeJudgmentService;

    @PostMapping("/judge")
    public ResponseEntity<String> judgeCode(
            @RequestParam("language") String language,
            @RequestParam("code") String code,
            @RequestBody List<TestCase> testCases) {
        String result = null;
        try {
            result = codeJudgmentService.judgeCode(language, code, testCases);
        } catch (IOException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}