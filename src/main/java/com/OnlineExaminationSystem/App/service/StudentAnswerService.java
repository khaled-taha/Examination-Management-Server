package com.OnlineExaminationSystem.App.service;


import com.OnlineExaminationSystem.App.entity.Exam.*;
import com.OnlineExaminationSystem.App.entity.dto.studentAnswer.CompleteStudentAnswerDto;
import com.OnlineExaminationSystem.App.entity.dto.studentAnswer.SelectedStudentAnswerDto;
import com.OnlineExaminationSystem.App.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
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
    private ExamService examService;


    /*
     * Show Exam
     * Create Exam Attempt.
     * Create a student answer
     * Exit Exam
     * Get all answers of exam
     * Create the result of the exam
     * */





    // create a Student answer for a question:
    @Async
    public void saveSelectedStudentAnswer(List<SelectedStudentAnswerDto> selectedAnswersDto, long attemptId){
        Optional<ExamAttempt> attempt = this.attemptRepository.findById(attemptId);
        List<StudentAnswer> studentAnswers = new ArrayList<>();

        selectedAnswersDto.stream().forEach((answer) -> {

                    // correct Answer
                    List<QuestionAnswer> correctedAnswers =
                            this.questionAnswerRepository.findAllByQuestionId(answer.getQuestionId());

                    // Are there student answers for this question ?
                    StudentAnswer studentAnswer = null;
                    if(correctedAnswers.size() > 0) {
                        studentAnswer = this.studentAnswerRepository.
                                findByExamAttemptIdAndQuestionId(attemptId, correctedAnswers.get(0).getQuestion().getId());
                    }


                    // selected Answers
                    List<QuestionAnswer> selectedAnswers = correctedAnswers.stream()
                            .filter((questionAnswer) -> answer.getAnswersIds().contains(questionAnswer.getId()))
                            .collect(Collectors.toList());


                    // get the total points
                    BigDecimal questionPoints = BigDecimal.valueOf(correctedAnswers.get(0).getQuestion().getPoints());

                    // count the corrected answer
                    int correctAnswerCount = (int) correctedAnswers.stream().filter(QuestionAnswer::isCorrectAnswer).count();

                    // count selected correct answer
                    int selectedAnswersCount = (int) selectedAnswers.stream().filter(QuestionAnswer::isCorrectAnswer).count();

                    // calculate the answerPoints = (totalPoints / CACounts) * SACount
                    BigDecimal answerPoints = (questionPoints.divide(BigDecimal.valueOf(correctAnswerCount)))
                            .multiply(BigDecimal.valueOf(selectedAnswersCount));

                    studentAnswers.add(
                            new StudentAnswer(
                                    studentAnswer != null ? studentAnswer.getId() : 0,
                                    selectedAnswers, attempt.get(),
                                    answerPoints.doubleValue()
                            ));
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

            // Are there student answers for this question ?
            StudentAnswer studentAnswer = null;
            if(correctedAnswers != null) {
                studentAnswer = this.studentAnswerRepository.
                        findByExamAttemptIdAndQuestionId(attemptId, correctedAnswers.getQuestion().getId());
            }

            // get the total points
            BigDecimal questionPoints = BigDecimal.valueOf(correctedAnswers.getQuestion().getPoints());


            boolean checkedAnswer = answer.getTextAnswer().equalsIgnoreCase(correctedAnswers.getAnswerText());
            // calculate the points of the answer
            double points = checkedAnswer ? questionPoints.doubleValue() : 0;

            studentAnswers.add(new StudentAnswer(
                    studentAnswer != null ? studentAnswer.getId() : 0,
                    Arrays.asList(correctedAnswers),
                    attempt,
                    points
            ));
        });

        this.studentAnswerRepository.saveAll(studentAnswers);
    }

    // Get all student answers of the exam: Done
    public List<StudentAnswer> getAllAnswers(long attemptId){
        return this.studentAnswerRepository.findAllByExamAttemptId(attemptId);
    }



}