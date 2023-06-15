package com.exam.code.Judgment.services;

import com.exam.code.Judgment.models.TestCase;
import org.springframework.stereotype.Service;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.*;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

@Service
public class JavaCodeJudgment {

    public String judgeJavaCode(String code, List<TestCase> testCases) throws IOException {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);

        // Create a temporary directory to store the Java source file
        File tempDir;
        try {
            tempDir = Files.createTempDirectory("java-code").toFile();
        } catch (IOException e) {
            e.printStackTrace();
            return "ERROR";
        }

        // Create a temporary Java source file with the provided code
        File sourceFile = new File(tempDir, "Solution.java");
        try {
            Files.write(sourceFile.toPath(), code.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
            return "ERROR";
        }

        // Compile the Java source file
        Iterable<? extends JavaFileObject> compilationUnits = fileManager.getJavaFileObjectsFromFiles(Collections.singletonList(sourceFile));
        JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, null, null, null, compilationUnits);
        boolean compilationResult = task.call();

        if (!compilationResult) {
            // Compilation failed
            return "COMPILATION_ERROR";
        }

        // Load the compiled class dynamically using a custom class loader
        ClassLoader classLoader = new URLClassLoader(new URL[]{tempDir.toURI().toURL()});
        Class<?> solutionClass;
        try {
            solutionClass = classLoader.loadClass("Solution");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return "ERROR";
        }

        // Iterate over test cases and execute the Java code for each test case
        for (TestCase testCase : testCases) {
            String expectedOutput = testCase.getExpectedOutput();
            String input = testCase.getInput();

            ByteArrayOutputStream outputStream = executeMainMethod(solutionClass, input);

            // Compare the output with the expected output for the current test case
            String actualOutput = outputStream.toString().trim();
            if (!expectedOutput.equals(actualOutput)) {
                return "WRONG_ANSWER";
            }
        }

        // All test cases passed successfully
        return "ACCEPTED";
    }


    private File createTempDirectory() throws IOException {
        return Files.createTempDirectory("java-code").toFile();
    }

    private File createJavaSourceFile(File tempDir, String code) {
        File sourceFile = new File(tempDir, "Solution.java");
        try {
            Files.write(sourceFile.toPath(), code.getBytes());
            return sourceFile;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private boolean compileJavaSourceFile(File sourceFile) {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);

        Iterable<? extends JavaFileObject> compilationUnits = fileManager.getJavaFileObjectsFromFiles(Collections.singletonList(sourceFile));
        JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, null, null, null, compilationUnits);
        return task.call();
    }

    private Class<?> loadCompiledClass(File tempDir, String className) {
        ClassLoader classLoader;
        try {
            classLoader = new URLClassLoader(new URL[]{tempDir.toURI().toURL()});
            return classLoader.loadClass(className);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private ByteArrayOutputStream executeMainMethod(Class<?> solutionClass, String input) {
        try {
            Object solutionInstance = solutionClass.getDeclaredConstructor().newInstance();
            Method mainMethod = solutionClass.getDeclaredMethod("main", String[].class);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            PrintStream printStream = new PrintStream(outputStream);
            PrintStream originalOut = System.out;
            System.setOut(printStream);

            // Prepare the input for the main method
            ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
            System.setIn(inputStream);

            mainMethod.invoke(solutionInstance, new Object[]{null});

            System.out.flush();
            System.setOut(originalOut);

            return outputStream;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }



    private String compareOutputWithTestCases(ByteArrayOutputStream outputStream, List<TestCase> testCases) {
        String[] actualOutputs = outputStream.toString().trim().split("\n");

        for (int i = 0; i < testCases.size(); i++) {
            String expectedOutput = testCases.get(i).getExpectedOutput();
            String actualOutput = actualOutputs[i].trim();

            if (!expectedOutput.equals(actualOutput)) {
                return "WRONG_ANSWER In test Case " + (i + 1);
            }
        }

        return "ACCEPTED";
    }


    private void cleanupTempFiles(File tempDir) {
        try {
            Files.walk(tempDir.toPath())
                    .map(Path::toFile)
                    .forEach(File::delete);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

