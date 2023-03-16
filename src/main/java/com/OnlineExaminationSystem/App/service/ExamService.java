package com.OnlineExaminationSystem.App.service;

import com.OnlineExaminationSystem.App.entity.Exam.Exam;
import com.OnlineExaminationSystem.App.entity.Exam.ExamAttempt;
import com.OnlineExaminationSystem.App.entity.Exam.Question;
import com.OnlineExaminationSystem.App.entity.Exam.QuestionType;
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
    public ExamAttempt attemptExam(long userId, long examId){
        Optional<Exam> exam =  this.examRepository.findExamById(examId);
        Optional<User> user = this.userRepository.findById(userId);


        if(user.isPresent() && exam.isPresent()
                && (LocalDateTime.now().equals(exam.get().getStartTime()) ||
                (LocalDateTime.now().isAfter(exam.get().getStartTime())))
                && (LocalDateTime.now().isBefore(exam.get().getEndTime()))) {

            ExamAttempt examAttempt = new ExamAttempt();
            examAttempt.setUser(user.get());
            examAttempt.setExam(exam.get());
            return this.attemptRepository.save(examAttempt);
        }
        return null;
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
        List<QuestionDto> questionDto = new ArrayList<>();

        questions.stream().forEach((question) -> {

            // set question answers
            List<QuestionAnswerDto> questionAnswerDto = new ArrayList<>();

            question.getQuestionAnswers().stream().forEach((answer) -> {

                questionAnswerDto.add(QuestionAnswerDto
                        .builder()
                        .id(answer.getId())
                        .answerText((answer.getQuestion().getQuestionType() != QuestionType.Matching)
                                ? answer.getAnswerText() : null)
                        .build());
            });

            // set questions
            questionDto.add(QuestionDto
                    .builder()
                    .id(question.getId())
                    .points(question.getPoints())
                    .questionType(question.getQuestionType().name())
                    .questionText(question.getQuestionText())
                    .questionAnswers(questionAnswerDto)
                    .build());
        });



        return ExamDto.builder()
                .id(exam.getId())
                .examName(exam.getExamName())
                .duration(exam.getDuration())
                .startTime(exam.getStartTime())
                .endTime(exam.getEndTime())
                .successRate(exam.getSuccessRate())
                .questions(questionDto)
                .build();
    }
    public List<Exam> getAllExamsByCourseId(Long courseId) {
        return examRepository.findAllExamsByCourseId(courseId);
    }
}
