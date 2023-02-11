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
    @CrossOrigin(origins = "*", originPatterns = ".*")
    public ResponseEntity<Exam> saveExam(@RequestBody Exam exam){
        Exam savedExam = this.examService.saveExam(exam);
        return new ResponseEntity<>(savedExam, HttpStatus.OK);
    }

    @GetMapping(path = "/getAll")
    @CrossOrigin(origins = "*", originPatterns = ".*")
    public ResponseEntity<List<Exam>> getExamById(){
        List<Exam> exams = this.examService.getAllExams();
        return new ResponseEntity<>(exams, HttpStatus.OK);
    }

    // get Exam By Id
    @GetMapping(path = "/{id}")
    @CrossOrigin(origins = "*", originPatterns = ".*")
    public ResponseEntity<Exam> getExamById(@PathVariable("id") long id){
        Exam exam = this.examService.getExamById(id);
        return new ResponseEntity<>(exam, HttpStatus.OK);
    }

    // delete exam
    @DeleteMapping(path = "/delete")
    @CrossOrigin(origins = "*", originPatterns = ".*")
    public ResponseEntity<?> deleteExam(@RequestBody Exam exam){
        this.examService.deleteExam(exam);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // save question
    @PostMapping(path = "/saveQuestion")
    @CrossOrigin(origins = "*", originPatterns = ".*")
    public ResponseEntity<Question> saveQuestion(@RequestBody Question question){
        Question savedQuestion = this.examService.saveQuestion(question);
        return new ResponseEntity<>(savedQuestion, HttpStatus.OK);
    }

    // delete question
    @DeleteMapping(path = "/deleteQuestion")
    @CrossOrigin(origins = "*", originPatterns = ".*")
    public ResponseEntity<?> deleteQuestion(@RequestBody Question question){
        this.examService.deleteQuestion(question);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // Attempt the exam
    @PostMapping(path = "/attemptExam")
    @CrossOrigin(origins = "*", originPatterns = ".*")
    public ResponseEntity<Exam> attemptExam(@RequestBody ExamAttempt examAttempt){
        ExamAttempt attempt = this.studentAnswerService.attemptExam(examAttempt);
        return new ResponseEntity<>(attempt.getExam(), HttpStatus.OK);
    }

    // create a Student answer for a question
    @PostMapping(path = "/saveStudentAnswer")
    @CrossOrigin(origins = "*", originPatterns = ".*")
    public ResponseEntity<?> saveStudentAnswer(@RequestBody StudentAnswer studentAnswer){
        this.studentAnswerService.createStudentAnswer(studentAnswer);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // Get all student answers of the exam
    @GetMapping(path = "/getAllStudentAnswers")
    @CrossOrigin(origins = "*", originPatterns = ".*")
    public ResponseEntity<List<StudentAnswer>> getAllStudentAnswers(){
        List<StudentAnswer> answers = this.studentAnswerService.getAllAnswers();
        return new ResponseEntity<>(answers, HttpStatus.OK);
    }

    // create the result
    @PostMapping(path = "/createResult")
    @CrossOrigin(origins = "*", originPatterns = ".*")
    public ResponseEntity<ExamResult> createResult(@RequestBody ExamResult examResult){
        ExamResult result = this.studentAnswerService.createResult(examResult);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // get the result of the student
    @GetMapping(path = "/getResult/{attemptId}")
    @CrossOrigin(origins = "*", originPatterns = ".*")
    public ResponseEntity<ExamResult> getResult(@PathVariable("attemptId") long attemptId){
        ExamResult result = this.studentAnswerService.getResult(attemptId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


}
