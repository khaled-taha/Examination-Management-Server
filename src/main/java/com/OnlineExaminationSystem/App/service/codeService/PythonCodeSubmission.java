package com.OnlineExaminationSystem.App.service.codeService;

import com.OnlineExaminationSystem.App.entity.Exam.questions.codeQuestion.TestCase;
import lombok.Data;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Data
public class PythonCodeSubmission extends JudgeCode{


    @Transactional
    @Override
    public void judgeCode(String code, List<TestCase> testCases, int timeLimit) {

        try {
            // Create a temporary directory to store the Java source file
            Path tempDir = Files.createTempDirectory("py-code");

            // Create the main class file with the provided code
            Path mainClass = createMainClass(tempDir, code);


            // Loop through the test cases

            for (TestCase testCase : testCases) {
                counter++;
                // Run the Java code and get the output
                Process runProcess = runCode(tempDir, testCase.getInput(), testCase.getExpectedOutput(), timeLimit);
                if (runProcess == null) {
                   continue;
                }

                // Read the output
                String testCaseOutput = readProcessMessage(runProcess.getInputStream());

                // Compare the output with the expected output
                if (!testCaseOutput.trim().equals(testCase.getExpectedOutput().trim())) {
                    // Wrong answer
                    submissionFlow.append("WRONG_ANSWER AT TEST CASE : " + counter).append("<br>")
                            .append("Input: ").append("<br>").append(testCase.getInput()).append("<br>")
                            .append("Expected Output: ").append("<br>").append(testCase.getExpectedOutput()).append("<br>")
                            .append("Actual Output: ").append("<br>").append(testCaseOutput)
                            .append("------------------------------------------------------").append("<br>");
                }else {
                    passedTestCases++;
                    points += testCase.getPoints();
                }
            }

            deleteDirectory(tempDir);

            // If all test cases pass, return "ACCEPTED"
            submissionFlow.append("ACCEPTED Test Cases : " + passedTestCases).append("<br>");
        } catch (Exception e) {
            e.printStackTrace();
            submissionFlow.append(e.getMessage()).append("<br>");
            status = 1;
        }
    }

    public  Path createMainClass(Path path, String code) throws IOException {
        if (!Files.isDirectory(path)) {
            path = Files.createDirectory(path);
        }
        Path mainClass = path.resolve("Main.py");
        Files.write(mainClass, code.getBytes());
        return mainClass;
    }

    public  Process runCode(Path path, String input, String output, int timeLimit) throws IOException, InterruptedException {
        String runtimeError = "";
        ProcessBuilder processBuilder = new ProcessBuilder("python", "-u", "Main.py");
        processBuilder.directory(path.toFile());
        Process process = processBuilder.start();

        // Write the inputs to the process's input stream
        OutputStream processInputStream = process.getOutputStream();
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(processInputStream));
        writer.write(input);
        writer.close();


        // Validate Code
        if (!process.waitFor(timeLimit, TimeUnit.SECONDS)) {
            submissionFlow.append("Time Limit Exceeded At Test Case : ").append(counter).append("<br>")
                    .append("Input: ").append("<br>").append(input).append("<br>")
                    .append("Expected Output: ").append("<br>").append(output).append("<br>")
                    .append("------------------------------------------------------").append("<br>");
            return null;
        } else if ((runtimeError = readProcessMessage(process.getErrorStream())).contains("Exception")) {

            submissionFlow.append("Runtime Error At Test Case : ").append(counter).append("<br>")
                    .append("Input: ").append("<br>").append(input).append("<br>")
                    .append("Expected Output: ").append("<br>").append(output).append("<br>")
                    .append(runtimeError).append("<br>")
                    .append("------------------------------------------------------").append("<br>");;
            return null;
        }

        return process;
    }

    public String readProcessMessage(InputStream inputStream) throws IOException {
        String line;
        StringBuilder output = new StringBuilder();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        while ((line = bufferedReader.readLine()) != null) {
            output.append(line).append('\n');
        }
        return output.toString();
    }

    public  void deleteDirectory(Path path) throws IOException {
        Files.walk(path)
                .sorted(Comparator.reverseOrder())
                .map(Path::toFile)
                .forEach(File::delete);
    }



}
