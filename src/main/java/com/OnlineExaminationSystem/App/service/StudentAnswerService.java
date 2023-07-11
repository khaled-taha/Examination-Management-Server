package com.OnlineExaminationSystem.App.service;


import com.OnlineExaminationSystem.App.entity.Exam.*;
import com.OnlineExaminationSystem.App.entity.Exam.questions.QuestionType;
import com.OnlineExaminationSystem.App.entity.Exam.questions.codeQuestion.CodeQuestion;
import com.OnlineExaminationSystem.App.entity.dto.studentAnswer.CodeStatusDto;
import com.OnlineExaminationSystem.App.entity.Exam.questions.codeQuestion.CodeStudentAnswer;
import com.OnlineExaminationSystem.App.entity.Exam.questions.standardQuestion.StandardQuestionAnswer;
import com.OnlineExaminationSystem.App.entity.Exam.questions.StudentAnswer;
import com.OnlineExaminationSystem.App.entity.Exam.questions.standardQuestion.StandardStudentAnswer;
import com.OnlineExaminationSystem.App.entity.dto.studentAnswer.CompleteStudentAnswerDto;
import com.OnlineExaminationSystem.App.entity.dto.studentAnswer.SelectedStudentAnswerDto;
import com.OnlineExaminationSystem.App.repository.*;
import com.OnlineExaminationSystem.App.repository.questionRepo.QuestionAnswerRepository;
import com.OnlineExaminationSystem.App.repository.questionRepo.StudentAnswerRepository;
import com.OnlineExaminationSystem.App.repository.questionRepo.codeQuestionRepo.CodeProblemRepository;
import com.OnlineExaminationSystem.App.repository.questionRepo.codeQuestionRepo.TestCaseRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class StudentAnswerService {

    @Autowired
    private StudentAnswerRepository studentAnswerRepository;

    @Autowired
    private ExamAttemptRepository attemptRepository;

    @Autowired
    private QuestionAnswerRepository questionAnswerRepository;

    @Autowired
    private final CodeProblemRepository codeProblemRepository;

    @Autowired
    private final TestCaseRepository testCaseRepository;

    public void saveSelectedStudentAnswer(List<SelectedStudentAnswerDto> selectedAnswersDto, long attemptId) {
        ExamAttempt attempt = attemptRepository.findById(attemptId).get();

        List<StudentAnswer> studentAnswers = selectedAnswersDto.stream().map(answer -> {

            List<StandardQuestionAnswer> answers = questionAnswerRepository
                    .findAllByQuestionId(answer.getQuestionId());

            if(answers == null || answers.size() == 0) return null;

            List<StandardQuestionAnswer> selectedAnswers = answers.stream()
                    .filter(questionAnswer -> answer.getAnswersIds().contains(questionAnswer.getId()))
                    .collect(Collectors.toList());

            int correctAnswerCount = (int) answers.stream().filter(StandardQuestionAnswer::isCorrectAnswer).count();
            int selectedAnswersCount = (int) selectedAnswers.stream().filter(StandardQuestionAnswer::isCorrectAnswer).count();

            BigDecimal questionPoints = BigDecimal.valueOf(answers.get(0).getQuestion().getPoints());
            BigDecimal answerPoints = questionPoints
                    .divide(BigDecimal.valueOf(correctAnswerCount))
                    .multiply(BigDecimal.valueOf(selectedAnswersCount));

            StudentAnswer studentAnswer = studentAnswerRepository
                    .findByExamAttemptIdAndQuestionId(attemptId, answer.getQuestionId());

            return new StandardStudentAnswer(
                    studentAnswer != null ? studentAnswer.getId() : 0,
                    selectedAnswers,
                    attempt,
                    answers.get(0).getQuestion(),
                    answerPoints.doubleValue(),
                    null
            );
        }).filter(Objects::nonNull).collect(Collectors.toList());
        studentAnswerRepository.saveAll(studentAnswers);
    }


    public void saveCompleteStudentAnswer(List<CompleteStudentAnswerDto> answers, long attemptId) {
        ExamAttempt attempt = attemptRepository.findById(attemptId).get();

        List<StudentAnswer> studentAnswers = answers.stream().map(answer -> {

            StandardQuestionAnswer correctedAnswer = questionAnswerRepository
                    .findByQuestionId(answer.getQuestionId());


            if (correctedAnswer == null) {
                return null;
            }

            BigDecimal questionPoints = BigDecimal.valueOf(correctedAnswer.getQuestion().getPoints());

            double answerPoints = removeHtmlTags(answer.getTextAnswer()).
                    equalsIgnoreCase(removeHtmlTags(correctedAnswer.getAnswerText())) ? questionPoints.doubleValue() : 0;

            StudentAnswer studentAnswer = studentAnswerRepository
                    .findByExamAttemptIdAndQuestionId(attemptId, answer.getQuestionId());

            return new StandardStudentAnswer(
                    studentAnswer != null ? studentAnswer.getId() : 0,
                    answerPoints != 0 ?Collections.singletonList(correctedAnswer) : new ArrayList<>(),
                    attempt,
                    correctedAnswer.getQuestion(),
                    answerPoints,
                    answer.getTextAnswer()
            );
        }).filter(Objects::nonNull).collect(Collectors.toList());
        studentAnswerRepository.saveAll(studentAnswers);
    }

    public CodeStatusDto judgeCode(long attemptId, long questionId, String language, String code) {
        Optional<CodeQuestion> question = this.codeProblemRepository.findById(questionId);
        CodeStudentAnswer answer = null;

        if(question.isPresent()){
            ExamAttempt attempt = attemptRepository.findById(attemptId).get();
            if(language.equalsIgnoreCase("java") || language.equalsIgnoreCase("python")){
                JavaCodeSubmission codeSubmission =  new JavaCodeSubmission();
                codeSubmission.judgeJavaCode(code, question.get().getTestCases(),question.get().getTimeLimit());
                String result = codeSubmission.getSubmissionFlow().toString();

                // Get CodeStudentAnswer of attemptId
                StudentAnswer codeStudentAnswer = this.studentAnswerRepository
                        .findByExamAttemptIdAndQuestionId(attemptId, questionId);

                if(codeStudentAnswer instanceof CodeStudentAnswer c){
                    answer = c;
                } else {
                    answer = new CodeStudentAnswer();
                }

                // Create Code Student Answer Object
                answer.setSubmission(result);
                answer.setStatus(codeSubmission.getStatus());
                answer.setPoints(codeSubmission.getPoints());
                answer.setPassedTestCases(codeSubmission.getPassedTestCases());
                answer.setQuestion(question.get());
                answer.setCode(code);
                answer.setExamAttempt(attempt);

                // Save the student answer
                CodeStudentAnswer savedAnswer =  studentAnswerRepository.save(answer);

            }
        }
        assert answer != null;
        return new CodeStatusDto(answer.getStatus(), answer.getSubmission(), answer.getCode());
    }

    public CodeStatusDto getSubmissionStatus(long attemptId, long questionId){
        StudentAnswer codeStudentAnswer = this.studentAnswerRepository
                .findByExamAttemptIdAndQuestionId(attemptId, questionId);

        if(codeStudentAnswer == null) return null;

        CodeStatusDto status = new CodeStatusDto();
        if(codeStudentAnswer instanceof CodeStudentAnswer c){
            status.setStatus(c.getStatus());
            status.setLog(c.getSubmission());
            status.setCode(c.getCode());
        }
        return status;
    }

    public List<CodeStatusDto> getAllSubmissionStatus(long attemptId){
        List<CodeStatusDto> answers = new ArrayList<>();
        List<StudentAnswer> codeStudentAnswer = this.studentAnswerRepository.findAllByExamAttemptId(attemptId);

        codeStudentAnswer.forEach(answer -> {
            if(answer != null && answer.getQuestion().getQuestionType().equals(QuestionType.CODING)
                    && codeStudentAnswer instanceof CodeStudentAnswer c){

                CodeStatusDto status = new CodeStatusDto();
                status.setStatus(c.getStatus());
                status.setLog(c.getSubmission());
                status.setCode(c.getCode());
                answers.add(status);
            }
        });
        return answers;
    }

    // Get all student answers of the exam: Done
    public List<StudentAnswer> getAllAnswers(long attemptId){
        return this.studentAnswerRepository.findAllByExamAttemptId(attemptId);
    }

    public static String removeHtmlTags(String htmlString) {
        // Remove HTML tags using regular expressions
        String pattern = "<.*?>";
        String textWithoutHtml = htmlString.replaceAll(pattern, "");

        return textWithoutHtml;
    }



}