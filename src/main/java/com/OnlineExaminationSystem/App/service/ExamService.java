package com.OnlineExaminationSystem.App.service;

import com.OnlineExaminationSystem.App.entity.Exam.Exam;
import com.OnlineExaminationSystem.App.entity.Exam.ExamAttempt;
import com.OnlineExaminationSystem.App.entity.Exam.Question;
import com.OnlineExaminationSystem.App.entity.dto.exam.ExamAttemptDto;
import com.OnlineExaminationSystem.App.entity.dto.exam.ExamDto;
import com.OnlineExaminationSystem.App.entity.dto.exam.QuestionAnswerDto;
import com.OnlineExaminationSystem.App.entity.dto.exam.QuestionDto;
import com.OnlineExaminationSystem.App.entity.users.User;
import com.OnlineExaminationSystem.App.exceptions.ApiException;
import com.OnlineExaminationSystem.App.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ExamService {

    @Autowired
    private ExamRepository examRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private ExamAttemptRepository attemptRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ExamResultRepository examResultRepository;


    public Exam saveExam(Exam exam){
        return this.examRepository.save(exam);
    }
    public List<Exam> getAllExams() {
        List<Exam> exams = this.examRepository.findAll();
        exams.stream().forEach((exam) -> {

            if((LocalDateTime.now().equals(exam.getStartTime()) || (LocalDateTime.now().isAfter(exam.getStartTime())))
                    && (LocalDateTime.now().isBefore(exam.getEndTime())))
                exam.setState(true);
            else
                exam.setState(false);
        });

        return exams;
    }
    public Exam getExamById(long id){
        Exam exam =  this.examRepository.findExamById(id)
                .orElseThrow(() -> new ApiException("Exam Not found"));

        if((LocalDateTime.now().equals(exam.getStartTime()) || (LocalDateTime.now().isAfter(exam.getStartTime())))
                && (LocalDateTime.now().isBefore(exam.getEndTime())))
            exam.setState(true);
        else
            exam.setState(false);
        return exam;
    }
    public void deleteExam(Exam exam){
        this.questionRepository.deleteAllByExamId(exam.getId());
        this.examRepository.delete(exam);
    }
    public void deleteExam(long id){
        this.examRepository.deleteById(id);
    }
    public ExamAttemptDto attemptExam(long userId, long examId){
        Optional<Exam> exam =  this.examRepository.findExamById(examId);
        Optional<User> user = this.userRepository.findById(userId);


        if(user.isPresent() && exam.isPresent()
                && (LocalDateTime.now().equals(exam.get().getStartTime()) ||
                (LocalDateTime.now().isAfter(exam.get().getStartTime())))
                && (LocalDateTime.now().isBefore(exam.get().getEndTime()))) {

            ExamAttempt examAttempt = new ExamAttempt();
            examAttempt.setUser(user.get());
            examAttempt.setExam(exam.get());
            examAttempt =  this.attemptRepository.save(examAttempt);
            return ExamAttemptDto.mapToExamAttemptDto(examAttempt);
        }
        throw new ApiException("Expired Exam");
    }
    public void endExam(long attemptId){
        Optional<ExamAttempt> attempt = this.attemptRepository.findById(attemptId);
        if(attempt.isPresent()) {
            attempt.get().setEndTime(LocalDateTime.now());
            this.attemptRepository.save(attempt.get());
        }
    }
    public List<Question> saveQuestions(List<Question> questions, Long examId) {

        Exam exam = this.examRepository.findExamById(examId).get();
        exam.setQuestions(questions);

        questions.stream().forEach(question -> {
            question.setExam(exam);
            question.getQuestionAnswers().stream().
                    forEach(answer -> answer.setQuestion(question));
        });
        return this.examRepository.save(exam).getQuestions();
    }
    public List<Question> getExamQuestions(long examId) {
        return this.questionRepository.findAllByExamId(examId);
    }
    public void deleteQuestion(Question question){
        this.questionRepository.delete(question);
    }
    public int getExamPoints(long examId){
        //get the exam
        Optional<Exam> exam = this.examRepository.findExamById(examId);

        // get all questions with answers
        List<Question> questions = exam.get().getQuestions();

        // get the total points of the exam
        return questions.stream().mapToInt(Question::getPoints).sum();
    }
    public ExamDto renderExam(long examId) {

        Exam exam = this.getExamById(examId);

        List<Question> questions = exam.getQuestions();
        List<QuestionDto> questionsDto = new ArrayList<>();

        questions.stream().forEach((question) -> {

            // set question answers
            List<QuestionAnswerDto> questionAnswerDto = new ArrayList<>();

            question.getQuestionAnswers().stream().forEach((answer) -> {
                questionAnswerDto.add(QuestionAnswerDto.mapToQuestionAnswer(answer));
            });

            // set questions
            questionsDto.add(QuestionDto.mapToQuestion(question, questionAnswerDto));
        });

        return ExamDto.mapToExam(exam, questionsDto);
    }
    public List<Exam> getAllExamsByCourseId(Long courseId) {

        List<Exam> exams = examRepository.findAllExamsByCourseId(courseId);
        exams.stream().forEach((exam) -> {

            if((LocalDateTime.now().equals(exam.getStartTime()) || (LocalDateTime.now().isAfter(exam.getStartTime())))
                    && (LocalDateTime.now().isBefore(exam.getEndTime())))
                exam.setState(true);
            else
                exam.setState(false);
        });

        return exams;
    }
    public List<ExamAttemptDto> getAllAttempts(Long studentId){
        List<ExamAttempt> examAttempts =  this.attemptRepository.getAllExamAttemptByUserId(studentId);
        return examAttempts.stream().map(ExamAttemptDto::mapToExamAttemptDto).collect(Collectors.toList());
    }
}
