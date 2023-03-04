package com.OnlineExaminationSystem.App.service;


import com.OnlineExaminationSystem.App.entity.Exam.*;
import com.OnlineExaminationSystem.App.entity.dto.CompleteStudentAnswerDto;
import com.OnlineExaminationSystem.App.entity.dto.SelectedStudentAnswerDto;
import com.OnlineExaminationSystem.App.entity.users.User;
import com.OnlineExaminationSystem.App.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class StudentAnswerService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ExamResultRepository examResultRepository;

    @Autowired
    private ExamRepository examRepository;

    @Autowired
    private StudentAnswerRepository studentAnswerRepository;

    @Autowired
    private ExamAttemptRepository attemptRepository;

    @Autowired
    private QuestionAnswerRepository questionAnswerRepository;

    @Autowired
    private ExamService examService;


    /*
     * Show Exam
     * Create Exam Attempt.
     * Create a student answer
     * Exit Exam
     * Get all answers of exam
     * Create the result of the exam
     * */


    // Attempt the exam: Done
    public ExamAttempt attemptExam(long userId, long examId){
        Optional<Exam> exam =  this.examRepository.findExamById(examId);
        Optional<User> user = this.userRepository.findById(userId);

        if(user.isPresent() && exam.isPresent()
                && exam.get().getStartTime().isAfter(LocalDateTime.now())
                && exam.get().getEndTime().isBefore(LocalDateTime.now())) {

            ExamAttempt examAttempt = new ExamAttempt();
            examAttempt.setUser(user.get());
            examAttempt.setExam(exam.get());
            return this.attemptRepository.save(examAttempt);
        }
        return null;
    }

    // End the exam: Done
    public ExamResult endExam(long attemptId){
        Optional<ExamAttempt> attempt = this.attemptRepository.findById(attemptId);
        if(attempt.isPresent()) {
            attempt.get().setEndTime(LocalDateTime.now());
            this.attemptRepository.save(attempt.get());
        }
        return this.getResult(attemptId);
    }


    // create a Student answer for a question:
    public void saveSelectedStudentAnswer(List<SelectedStudentAnswerDto> selectedAnswersDto, long attemptId){
        Optional<ExamAttempt> attempt = this.attemptRepository.findById(attemptId);
        List<StudentAnswer> studentAnswers = new ArrayList<>();

        selectedAnswersDto.stream().forEach((answer) -> {

                    // correct Answer
                    List<QuestionAnswer> correctedAnswers =
                            this.questionAnswerRepository.findAllByQuestionId(answer.getQuestionId())
                                    .stream().filter(QuestionAnswer::isCorrectAnswer).collect(Collectors.toList());

                    // selected Answers
                    List<QuestionAnswer> selectedAnswers = correctedAnswers.stream()
                            .filter((questionAnswer) -> answer.getAnswersId().contains(questionAnswer.getId()))
                            .collect(Collectors.toList());

                    // get the total points
                    BigDecimal questionPoints = BigDecimal.valueOf(correctedAnswers.get(0).getQuestion().getPoints());

                    // count the corrected answer: correctedAnswers.size();

                    // count selected correct answer
                    int selectedAnswersCount = (int) selectedAnswers.stream().filter(QuestionAnswer::isCorrectAnswer).count();

                    // calculate the answerPoints = (totalPoints / CACounts) * SACount
                    BigDecimal answerPoints = (questionPoints.divide(BigDecimal.valueOf(correctedAnswers.size())))
                            .multiply(BigDecimal.valueOf(selectedAnswersCount));

                    studentAnswers.add(new StudentAnswer(selectedAnswers, attempt.get(), answerPoints.doubleValue()));
                }
        );

        this.studentAnswerRepository.saveAll(studentAnswers);
    }

    // save complete student answer
    public void saveCompleteStudentAnswer(List<CompleteStudentAnswerDto> answers, long attemptId){
        ExamAttempt attempt = this.attemptRepository.findById(attemptId).get();
        List<StudentAnswer> studentAnswers = new ArrayList<>();

        answers.stream().forEach((answer) -> {

            // correct Answer
            QuestionAnswer correctedAnswers =
                    this.questionAnswerRepository.findById(answer.getQuestionId()).get();

            // get the total points
            BigDecimal questionPoints = BigDecimal.valueOf(correctedAnswers.getQuestion().getPoints());


            boolean checkedAnswer = answer.getTextAnswer().equalsIgnoreCase(correctedAnswers.getAnswerText());
            // calculate the points of the answer
            double points = checkedAnswer ? questionPoints.doubleValue() : 0;

            studentAnswers.add(new StudentAnswer(Arrays.asList(correctedAnswers), attempt, points));
        });

        this.studentAnswerRepository.saveAll(studentAnswers);
    }

    // Get all student answers of the exam: Done
    public List<StudentAnswer> getAllAnswers(long attemptId){
        return this.studentAnswerRepository.findAllByExamAttemptId(attemptId);
    }



    // create the result: Done
    public ExamResult createResult(long attemptId){
        // get exam attempt
        Optional<ExamAttempt> attempt = this.attemptRepository.findById(attemptId);

        int points = this.examService.getExamPoints(attempt.get().getExam().getId());
        double score = this.calculateResult(attemptId, points);


        // create the result
        ExamResult result = new ExamResult();
        result.setScore(score);
        result.setPassed(result.getScore() >= attempt.get().getExam().getSuccessRate());
        result.setExamAttempt(attempt.get());

        return this.examResultRepository.save(result);
    }

    // get the result of the student
    public ExamResult getResult(long examAttemptId){
        return this.examResultRepository.getExamResultByExamAttemptId(examAttemptId);
    }

    // calculate the result
    private double calculateResult(long attemptId, int examPoints){
        // get all student answers
        List<StudentAnswer> answers = this.getAllAnswers(attemptId);

        // get the student score
        double score = answers.stream().mapToDouble(StudentAnswer::getPoints).sum();

        // get final score
        BigDecimal examResult = BigDecimal.valueOf(score).
                divide(BigDecimal.valueOf(examPoints))
                .multiply(BigDecimal.valueOf(100));

        return examResult.doubleValue();
    }



}