package com.OnlineExaminationSystem.App.controller;

import com.OnlineExaminationSystem.App.entity.Exam.*;
import com.OnlineExaminationSystem.App.entity.Exam.questions.codeQuestion.CodeQuestion;
import com.OnlineExaminationSystem.App.entity.dto.studentAnswer.CodeStatusDto;
import com.OnlineExaminationSystem.App.entity.Exam.questions.codeQuestion.TestCase;
import com.OnlineExaminationSystem.App.entity.Exam.questions.standardQuestion.StandardQuestionAnswer;
import com.OnlineExaminationSystem.App.entity.Exam.questions.standardQuestion.StandardQuestion;
import com.OnlineExaminationSystem.App.entity.dto.exam.ExamAttemptDto;
import com.OnlineExaminationSystem.App.entity.dto.exam.ExamDto;
import com.OnlineExaminationSystem.App.entity.dto.studentAnswer.CompleteStudentAnswerDto;
import com.OnlineExaminationSystem.App.entity.dto.studentAnswer.ExamResultDto;
import com.OnlineExaminationSystem.App.entity.dto.studentAnswer.SelectedStudentAnswerDto;
import com.OnlineExaminationSystem.App.entity.dto.studentAnswer.StudentAnswerDto;
import com.OnlineExaminationSystem.App.exceptions.ApiException;
import com.OnlineExaminationSystem.App.service.*;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/exam")
public class ExamController {

    @Autowired
    private ExamService examService;
    @Autowired
    private StudentAnswerService studentAnswerService;
    @Autowired
    private StandardQuestionService standardQuestionService;
    @Autowired
    private final CodeQuestionService codeProblemService;
    @Autowired
    private ResultService resultService;

    // save exam
    @PostMapping(path = "/save") // Add Exam
    public ResponseEntity<Exam> saveExam(@RequestBody Exam exam){
        Exam savedExam = this.examService.saveExam(exam);
        return new ResponseEntity<>(savedExam, HttpStatus.OK);
    }

    @GetMapping(path = "/getAllExams") // Show Exams List
    public ResponseEntity<List<Exam>> getAll(){
        List<Exam> exams = this.examService.getAllExams();
        return new ResponseEntity<>(exams, HttpStatus.OK);
    }

    // get Exam By Id
    @GetMapping(path = "/getExam/{id}") // Show Exam
    public ResponseEntity<Exam> getExamById(@PathVariable("id") long id){
        Exam exam = this.examService.getExamById(id);
        return new ResponseEntity<>(exam, HttpStatus.OK);
    }

    // delete exam
    @DeleteMapping(path = "/delete") // Delete Exam
    public ResponseEntity<?> deleteExam(@RequestBody Exam exam){
        this.examService.deleteExam(exam);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(path = "/delete/{examId}") // Delete Exam
    public ResponseEntity<?> deleteExam(@PathVariable("examId") Long examId){
        this.examService.deleteExam(examId);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    // save question
    @PostMapping(path = "/saveStandardQuestions/{examId}") // Save Question
    public ResponseEntity<List<StandardQuestion>> saveStandardQuestions(@RequestBody List<StandardQuestion> standardQuestions,
                                                                @PathVariable("examId") Long examId){
        List<StandardQuestion> savedQuestions = this.standardQuestionService.saveQuestions(standardQuestions, examId);
        return new ResponseEntity<>(savedQuestions, HttpStatus.OK);
    }

    // get Exam Questions
    @GetMapping(path = "/getStandardQuestions/{examId}") // Show Question
    public ResponseEntity<List<StandardQuestion>> getStandardQuestions(@PathVariable("examId") long examId){
        List<StandardQuestion> examQuestions = this.standardQuestionService.getStandardQuestions(examId);
        return new ResponseEntity<>(examQuestions, HttpStatus.OK);
    }

    // delete question
    @DeleteMapping(path = "/deleteStandardQuestion") // Delete Question
    public ResponseEntity<?> deleteStandardQuestion(@RequestBody StandardQuestion question){
        this.standardQuestionService.deleteStandardQuestion(question);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(path = "/deleteStandardQuestionAnswer") // Delete Answer
    public ResponseEntity<?> deleteStandardQuestionAnswer(@RequestBody List<StandardQuestionAnswer> questionAnswers){
        this.standardQuestionService.deleteStandardQuestionAnswer(questionAnswers);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/saveCodeQuestion/{examId}") // Save Question
    public ResponseEntity<List<CodeQuestion>> saveCodeQuestion(@RequestBody List<CodeQuestion> codeQuestions,
                                                                @PathVariable("examId") Long examId) {
        List<CodeQuestion> createdProblem = codeProblemService.saveCodeProblem(codeQuestions, examId);
        return new ResponseEntity<>(createdProblem, HttpStatus.CREATED);
    }

    @GetMapping("/getCodeQuestions/{examId}") // Show Question
    public ResponseEntity<List<CodeQuestion>> getCodeQuestions(@PathVariable("examId") Long examId) {
        List<CodeQuestion> codeProblem = codeProblemService.getCodeProblemByExamId(examId);
        if (codeProblem != null) {
            return new ResponseEntity<>(codeProblem, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/deleteCodeQuestion/{id}") // Delete Question
    public ResponseEntity<Void> deleteCodeQuestion(@PathVariable("id") Long id) {
        codeProblemService.deleteCodeProblem(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/deleteTestCases") // Delete Answer
    public ResponseEntity<Void> deleteTestCases(@RequestBody List<TestCase> testCases) {
        codeProblemService.deleteTestCases(testCases);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // Attempt the exam
    @PostMapping(path = "/attemptExam/{examId}/{userId}") // Attempt Exam
    public ResponseEntity<ExamAttemptDto> attemptExam(@PathVariable("examId") long examId,
                                                      @PathVariable("userId") long userId){
        ExamAttemptDto attempt = this.examService.attemptExam(userId, examId);
        return new ResponseEntity<>(attempt, HttpStatus.OK);
    }

    @PostMapping(path = "/testExam/{examId}/{userId}") // testExam Exam
    public ResponseEntity<ExamAttemptDto> testExam(@PathVariable("examId") long examId,
                                                      @PathVariable("userId") long userId){
        ExamAttemptDto attempt = this.examService.testExam(userId, examId);
        return new ResponseEntity<>(attempt, HttpStatus.OK);
    }

    // create a Student answer for a question {Selection}
    @PostMapping(path = "/saveSelectedStudentAnswer/{attemptId}") // Solve Exam
    public ResponseEntity<?> saveSelectedStudentAnswer(@RequestBody List<SelectedStudentAnswerDto> selectedAnswersDto,
                                                       @PathVariable("attemptId") long attemptId){
        this.studentAnswerService.saveSelectedStudentAnswer(selectedAnswersDto, attemptId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // create a Student answer for a question {Complete}
    @PostMapping(path = "/saveCompleteStudentAnswer/{attemptId}") // Solve Exam
    public ResponseEntity<?> saveCompleteStudentAnswer(@RequestBody List<CompleteStudentAnswerDto> selectedAnswersDto,
                                                       @PathVariable("attemptId") long attemptId){
        this.studentAnswerService.saveCompleteStudentAnswer(selectedAnswersDto, attemptId);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @PostMapping("/judgeCodeQuestion")
    public Mono<ResponseEntity<CodeStatusDto>> judgeCode(
            @RequestParam("attemptId") long attemptId,
            @RequestParam("questionId") long questionId,
            @RequestParam("language") String language,
            @RequestParam("code") String code) {
        try {
           CodeStatusDto statusDto = studentAnswerService.judgeCode(attemptId, questionId, language, code);
            return Mono.just(new ResponseEntity<>(statusDto, HttpStatus.OK));
        }catch (Exception e){
            throw new ApiException(e.getMessage());
        }

    }

    @Operation(summary = "Status = {-1 : In Progress, 0 : Compilation Error, 1 : Accepted}")
    @GetMapping(path = "/getCodeStatus/{attemptId}/{questionId}")
    public ResponseEntity<CodeStatusDto> getCodeStatus( @PathVariable("attemptId") long attemptId,
                                                        @PathVariable("questionId") long questionId) {
        return new ResponseEntity<>(this.studentAnswerService.getSubmissionStatus(attemptId,
                questionId), HttpStatus.OK);
    }

    @Operation(summary = "Status = {-1 : In Progress, 0 : Compilation Error, 1 : Accepted}")
    @GetMapping(path = "/getAllCodeStatus/{attemptId}")
    public ResponseEntity<List<CodeStatusDto>> getAllCodeStatus( @PathVariable("attemptId") long attemptId) {
        return new ResponseEntity<>(this.studentAnswerService.getAllSubmissionStatus(attemptId), HttpStatus.OK);
    }

    // end the exam
    @PostMapping(path = "/endExam/{examAttemptId}") // Solve Exam
    public ResponseEntity<?> endExam(@PathVariable("examAttemptId") long examAttemptId){
        this.examService.endExam(examAttemptId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // Get all student answers of the exam
    @GetMapping(path = "/getAllStudentAnswers/{examAttemptId}") // Solve Exam
    public ResponseEntity<StudentAnswerDto> getAllStudentAnswers(@PathVariable("examAttemptId") long examAttemptId){
        StudentAnswerDto answers = new StudentAnswerDto()
                .mapToStudentAnswerDto(this.studentAnswerService.getAllAnswers(examAttemptId));
        return new ResponseEntity<>(answers, HttpStatus.OK);
    }

    // create the result
    @PostMapping(path = "/createResult/{examAttemptId}") // Solve Exam
    public ResponseEntity<ExamResultDto> createResult(@PathVariable("examAttemptId") long examAttemptId){
        ExamResultDto result = this.resultService.createResult(examAttemptId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // get users that Attempt Exam
    @GetMapping(path = "/usersAttemptedExam/{examId}") // Show Attempts
    public ResponseEntity<List<ExamResultDto>> findAllUsersAttemptedExam(
            @PathVariable("examId") long examId
    ) {
        List<ExamResultDto> usersAttemptedExam = this.resultService
                .findAllUsersAttemptedExam(examId);
        return new ResponseEntity<>(usersAttemptedExam, HttpStatus.OK);
    }


    // get the result of the student
    @GetMapping(path = "/getResult/{attemptId}") // Show Result
    public ResponseEntity<ExamResult> getResult(@PathVariable("attemptId") long attemptId){
        ExamResult result = this.resultService.getResult(attemptId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(path = "/renderExam/{examId}") // Solve Exam
    public ResponseEntity<ExamDto> renderExam(@PathVariable("examId") long examId){
        ExamDto exam = this.examService.renderExam(examId);
        return new ResponseEntity<>(exam, HttpStatus.OK);
    }

    @GetMapping(path = "/getAllCourseExams/{courseId}") // Show Course Exams
    public ResponseEntity<List<Exam>> getAllExamsByCourseId(@PathVariable long courseId) {
        List<Exam> exams = examService.getAllExamsByCourseId(courseId);
        return ResponseEntity.ok(exams);
    }

    @Operation(summary = "To get All Attempts of the user by his id")
    @GetMapping(path = "/attempts/{userId}") // Show Attempts of User
    public ResponseEntity<List<ExamAttemptDto>> getAllAttempts(@PathVariable("userId") long userId){
        List<ExamAttemptDto> attempts = this.examService.getAllAttempts(userId);
        return new ResponseEntity<>(attempts, HttpStatus.OK);
    }
}
