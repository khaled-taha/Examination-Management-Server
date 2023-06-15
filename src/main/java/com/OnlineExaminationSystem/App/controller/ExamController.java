package com.OnlineExaminationSystem.App.controller;

import com.OnlineExaminationSystem.App.entity.Exam.*;
import com.OnlineExaminationSystem.App.entity.dto.exam.ExamAttemptDto;
import com.OnlineExaminationSystem.App.entity.dto.exam.ExamDto;
import com.OnlineExaminationSystem.App.entity.dto.studentAnswer.CompleteStudentAnswerDto;
import com.OnlineExaminationSystem.App.entity.dto.studentAnswer.ExamResultDto;
import com.OnlineExaminationSystem.App.entity.dto.studentAnswer.SelectedStudentAnswerDto;
import com.OnlineExaminationSystem.App.service.ExamService;
import com.OnlineExaminationSystem.App.service.ResultService;
import com.OnlineExaminationSystem.App.service.StudentAnswerService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    private ResultService resultService;

    // save exam
    @PostMapping(path = "/save")
    public ResponseEntity<Exam> saveExam(@RequestBody Exam exam){
        Exam savedExam = this.examService.saveExam(exam);
        return new ResponseEntity<>(savedExam, HttpStatus.OK);
    }

    @GetMapping(path = "/getAll")
    public ResponseEntity<List<Exam>> getAll(){
        List<Exam> exams = this.examService.getAllExams();
        return new ResponseEntity<>(exams, HttpStatus.OK);
    }

    // get Exam By Id
    @GetMapping(path = "/{id}")
    public ResponseEntity<Exam> getExamById(@PathVariable("id") long id){
        Exam exam = this.examService.getExamById(id);
        return new ResponseEntity<>(exam, HttpStatus.OK);
    }

    // delete exam
    @DeleteMapping(path = "/delete")
    public ResponseEntity<?> deleteExam(@RequestBody Exam exam){
        this.examService.deleteExam(exam);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(path = "/delete/{examId}")
    public ResponseEntity<?> deleteExam(@PathVariable("examId") Long examId){
        this.examService.deleteExam(examId);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    // save question
    @PostMapping(path = "/saveQuestions/{examId}")
    public ResponseEntity<List<Question>> saveQuestions(@RequestBody List<Question> questions,
                                                        @PathVariable("examId") Long examId){
        List<Question> savedQuestions = this.examService.saveQuestions(questions, examId);
        return new ResponseEntity<>(savedQuestions, HttpStatus.OK);
    }

    // get Exam Questions
    @GetMapping(path = "/getQuestions/{id}")
    public ResponseEntity<List<Question>> saveQuestions(@PathVariable("id") long examId){
        List<Question> examQuestions = this.examService.getExamQuestions(examId);
        return new ResponseEntity<>(examQuestions, HttpStatus.OK);
    }

    // delete question
    @DeleteMapping(path = "/deleteQuestion")
    public ResponseEntity<?> deleteQuestion(@RequestBody Question question){
        this.examService.deleteQuestion(question);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(path = "/deleteQuestionAnswer")
    public ResponseEntity<?> deleteQuestionAnswer(@RequestBody List<QuestionAnswer> questionAnswers){
        this.examService.deleteQuestionAnswer(questionAnswers);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // Attempt the exam
    @PostMapping(path = "/attemptExam/{examId}/{userId}")
    public ResponseEntity<ExamAttemptDto> attemptExam(@PathVariable("examId") long examId,
                                                      @PathVariable("userId") long userId){
        ExamAttemptDto attempt = this.examService.attemptExam(userId, examId);
        return new ResponseEntity<>(attempt, HttpStatus.OK);
    }

    // create a Student answer for a question {Selection}
    @PostMapping(path = "/saveSelectedStudentAnswer/{attemptId}")
    public ResponseEntity<?> saveSelectedStudentAnswer(@RequestBody List<SelectedStudentAnswerDto> selectedAnswersDto,
                                                       @PathVariable("attemptId") long attemptId){
        this.studentAnswerService.saveSelectedStudentAnswer(selectedAnswersDto, attemptId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // create a Student answer for a question {Complete}
    @PostMapping(path = "/saveCompleteStudentAnswer/{attemptId}")
    public ResponseEntity<?> saveCompleteStudentAnswer(@RequestBody List<CompleteStudentAnswerDto> selectedAnswersDto,
                                                       @PathVariable("attemptId") long attemptId){
        this.studentAnswerService.saveCompleteStudentAnswer(selectedAnswersDto, attemptId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // end the exam
    @PostMapping(path = "/endExam/{examAttemptId}")
    public ResponseEntity<?> endExam(@PathVariable("examAttemptId") long examAttemptId){
        this.examService.endExam(examAttemptId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // Get all student answers of the exam
    @GetMapping(path = "/getAllStudentAnswers/{examAttemptId}")
    public ResponseEntity<List<StudentAnswer>> getAllStudentAnswers(@PathVariable("examAttemptId") long examAttemptId){
        List<StudentAnswer> answers = this.studentAnswerService.getAllAnswers(examAttemptId);
        return new ResponseEntity<>(answers, HttpStatus.OK);
    }

    // create the result
    @PostMapping(path = "/createResult/{examAttemptId}")
    public ResponseEntity<ExamResultDto> createResult(@PathVariable("examAttemptId") long examAttemptId){
        ExamResultDto result = this.resultService.createResult(examAttemptId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // get users that Attempt Exam
    @GetMapping(path = "/usersAttemptedExam/{examId}")
    public ResponseEntity<List<ExamResultDto>> findAllUsersAttemptedExam(@PathVariable("examId") long examId){
        List<ExamResultDto> usersAttemptedExam =  this.resultService.findAllUsersAttemptedExam(examId);
        return new ResponseEntity<>(usersAttemptedExam, HttpStatus.OK);
    }

    // get the result of the student
    @GetMapping(path = "/getResult/{attemptId}")
    public ResponseEntity<ExamResult> getResult(@PathVariable("attemptId") long attemptId){
        ExamResult result = this.resultService.getResult(attemptId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(path = "/renderExam/{examId}")
    public ResponseEntity<ExamDto> renderExam(@PathVariable("examId") long examId){
        ExamDto exam = this.examService.renderExam(examId);
        return new ResponseEntity<>(exam, HttpStatus.OK);
    }

    @GetMapping(path = "/getAll/{courseId}")
    public ResponseEntity<List<Exam>> getAllExamsByCourseId(@PathVariable long courseId) {
        List<Exam> exams = examService.getAllExamsByCourseId(courseId);
        return ResponseEntity.ok(exams);
    }

    @Operation(summary = "To get All Attempts of the user by his id")
    @GetMapping(path = "/attempts/{userId}")
    public ResponseEntity<List<ExamAttemptDto>> getAllAttempts(@PathVariable("userId") long userId){
        List<ExamAttemptDto> attempts = this.examService.getAllAttempts(userId);
        return new ResponseEntity<>(attempts, HttpStatus.OK);
    }



}
