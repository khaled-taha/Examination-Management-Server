package com.OnlineExaminationSystem.App.controller;

import com.OnlineExaminationSystem.App.entity.Exam.*;
import com.OnlineExaminationSystem.App.service.ExamService;
import com.OnlineExaminationSystem.App.service.StudentAnswerService;
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

    // save exam
    @PostMapping(path = "/save")
    public ResponseEntity<Exam> saveExam(@RequestBody Exam exam){
        Exam savedExam = this.examService.saveExam(exam);
        return new ResponseEntity<>(savedExam, HttpStatus.OK);
    }

    @GetMapping(path = "/getAll")
    public ResponseEntity<List<Exam>> getExamById(){
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

    // save question
    @PostMapping(path = "/saveQuestions")
    public ResponseEntity<List<Question>> saveQuestions(@RequestBody List<Question> questions){
        List<Question> savedQuestions = this.examService.saveQuestions(questions);
        return new ResponseEntity<>(savedQuestions, HttpStatus.OK);
    }

    // get Questions of an exam
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

    // Attempt the exam
    @PostMapping(path = "/attemptExam")
    public ResponseEntity<Exam> attemptExam(@RequestBody ExamAttempt examAttempt){
        ExamAttempt attempt = this.studentAnswerService.attemptExam(examAttempt);
        return new ResponseEntity<>(attempt.getExam(), HttpStatus.OK);
    }

    // create a Student answer for a question
    @PostMapping(path = "/saveStudentAnswer")
    public ResponseEntity<?> saveStudentAnswer(@RequestBody StudentAnswer studentAnswer){
        this.studentAnswerService.createStudentAnswer(studentAnswer);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // Get all student answers of the exam
    @GetMapping(path = "/getAllStudentAnswers")
    public ResponseEntity<List<StudentAnswer>> getAllStudentAnswers(){
        List<StudentAnswer> answers = this.studentAnswerService.getAllAnswers();
        return new ResponseEntity<>(answers, HttpStatus.OK);
    }

    // create the result
    @PostMapping(path = "/createResult")
    public ResponseEntity<ExamResult> createResult(@RequestBody ExamResult examResult){
        ExamResult result = this.studentAnswerService.createResult(examResult);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // get the result of the student
    @GetMapping(path = "/getResult/{attemptId}")
    public ResponseEntity<ExamResult> getResult(@PathVariable("attemptId") long attemptId){
        ExamResult result = this.studentAnswerService.getResult(attemptId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


}
