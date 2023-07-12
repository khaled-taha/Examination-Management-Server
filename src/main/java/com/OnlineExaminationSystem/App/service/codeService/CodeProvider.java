package com.OnlineExaminationSystem.App.service.codeService;

public class CodeProvider {
    private String language;

    public static JudgeCode judgeCode(String language) {
        return switch (language){
            case "java" -> new JavaCodeSubmission();
            case "python" -> new PythonCodeSubmission();
            default -> null;
        };
    }




}
