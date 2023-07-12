package com.OnlineExaminationSystem.App.service.codeService;

import com.OnlineExaminationSystem.App.entity.Exam.questions.codeQuestion.TestCase;
import lombok.Data;

import java.util.List;

@Data
public abstract class JudgeCode {

    protected short status = -1;
    protected StringBuilder submissionFlow = new StringBuilder();
    protected int passedTestCases = 0;
    protected int counter = 0;
    protected int points = 0;

    public abstract void judgeCode(String code, List<TestCase> testCases, int timeLimit);
}
