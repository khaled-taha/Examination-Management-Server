package com.OnlineExaminationSystem.App.service;


import com.OnlineExaminationSystem.App.entity.Exam.ExamAttempt;
import com.OnlineExaminationSystem.App.entity.Exam.ExamResult;
import com.OnlineExaminationSystem.App.entity.Exam.StudentAnswer;
import com.OnlineExaminationSystem.App.repository.ExamAttemptRepository;
import com.OnlineExaminationSystem.App.repository.ExamResultRepository;
import com.OnlineExaminationSystem.App.repository.StudentAnswerRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class StudentAnswerService {

    @Autowired
    private ExamResultRepository examResultRepository;

    @Autowired
    private StudentAnswerRepository studentAnswerRepository;

    @Autowired
    private ExamAttemptRepository attemptRepository;


    /*
    * create a student answer
    * get all answers of exam
    * create the result of the exam
    * */


    // Attempt the exam
    public ExamAttempt attemptExam(ExamAttempt examAttempt){
        return this.attemptRepository.save(examAttempt);
    }

    // create a Student answer for a question
    public void createStudentAnswer(StudentAnswer studentAnswer){
        studentAnswer.setExamAttempt(studentAnswer.getExamAttempt());
        this.studentAnswerRepository.save(studentAnswer);
    }

    // Get all student answers of the exam
    public List<StudentAnswer> getAllAnswers(){
        return this.studentAnswerRepository.findAll();
    }

    // create the result
    public ExamResult createResult(ExamResult examResult){
        return this.examResultRepository.save(examResult);
    }

    // get the result of the student
    public ExamResult getResult(long examAttemptId){
        return this.examResultRepository.getExamResultByExamAttemptId(examAttemptId);
    }


}
