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



    // create a Student answer for a question:

    /*
    @Async
    public void saveSelectedStudentAnswer(List<SelectedStudentAnswerDto> selectedAnswersDto, long attemptId){
        Optional<ExamAttempt> attempt = this.attemptRepository.findById(attemptId);
        List<StudentAnswer> studentAnswers = new ArrayList<>();

        selectedAnswersDto.stream().forEach((answer) -> {

                    // Answers
                    List<QuestionAnswer> answers =
                            this.questionAnswerRepository.findAllByQuestionId(answer.getQuestionId());

                    // Are there student answers for this question ?
                    StudentAnswer studentAnswer = null;
                    if(answers.size() > 0) {
                        studentAnswer = this.studentAnswerRepository.
                                findByExamAttemptIdAndQuestionId(attemptId, answer.getQuestionId());
                    }



                    // selected Answers
                    List<QuestionAnswer> selectedAnswers = answers.stream()
                            .filter((questionAnswer) -> answer.getAnswersIds().contains(questionAnswer.getId()))
                            .collect(Collectors.toList());


                    // get the total points
                    BigDecimal questionPoints = BigDecimal.valueOf(answers.get(0).getQuestion().getPoints());


                    // count the corrected answer
                    int correctAnswerCount = (int) answers.stream().filter(QuestionAnswer::isCorrectAnswer).count();

                    // count selected correct answer
                    int selectedAnswersCount = (int) selectedAnswers.stream().filter(QuestionAnswer::isCorrectAnswer).count();

                    // calculate the answerPoints = (totalPoints / CACounts) * SACount
                    BigDecimal answerPoints = (questionPoints.divide(BigDecimal.valueOf(correctAnswerCount)))
                            .multiply(BigDecimal.valueOf(selectedAnswersCount));

                    studentAnswers.add(
                            new StudentAnswer(
                                    studentAnswer != null ? studentAnswer.getId() : 0,
                                    selectedAnswers, attempt.get(),
                                    answers.get(0).getQuestion(),
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
                        findByExamAttemptIdAndQuestionId(attemptId, answer.getQuestionId());
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
                    correctedAnswers.getQuestion(),
                    points
            ));
        });

        this.studentAnswerRepository.saveAll(studentAnswers);
    }
*/
    @Async
    public void saveSelectedStudentAnswer(List<SelectedStudentAnswerDto> selectedAnswersDto, long attemptId) {
        ExamAttempt attempt = attemptRepository.findById(attemptId).get();

        List<StudentAnswer> studentAnswers = selectedAnswersDto.stream().map(answer -> {

            List<QuestionAnswer> answers = questionAnswerRepository
                    .findAllByQuestionId(answer.getQuestionId());

            if(answers == null || answers.size() == 0) return null;

            List<QuestionAnswer> selectedAnswers = answers.stream()
                    .filter(questionAnswer -> answer.getAnswersIds().contains(questionAnswer.getId()))
                    .collect(Collectors.toList());

            int correctAnswerCount = (int) answers.stream().filter(QuestionAnswer::isCorrectAnswer).count();
            int selectedAnswersCount = (int) selectedAnswers.stream().filter(QuestionAnswer::isCorrectAnswer).count();

            BigDecimal questionPoints = BigDecimal.valueOf(answers.get(0).getQuestion().getPoints());
            BigDecimal answerPoints = questionPoints
                    .divide(BigDecimal.valueOf(correctAnswerCount))
                    .multiply(BigDecimal.valueOf(selectedAnswersCount));

            StudentAnswer studentAnswer = studentAnswerRepository
                    .findByExamAttemptIdAndQuestionId(attemptId, answer.getQuestionId());

            return new StudentAnswer(
                    studentAnswer != null ? studentAnswer.getId() : 0,
                    selectedAnswers,
                    attempt,
                    answers.get(0).getQuestion(),
                    answerPoints.doubleValue()
            );
        }).filter(Objects::nonNull).collect(Collectors.toList());
        studentAnswerRepository.saveAll(studentAnswers);
    }

    @Async
    public void saveCompleteStudentAnswer(List<CompleteStudentAnswerDto> answers, long attemptId) {
        ExamAttempt attempt = attemptRepository.findById(attemptId).get();

        List<StudentAnswer> studentAnswers = answers.stream().map(answer -> {
            QuestionAnswer correctedAnswer = questionAnswerRepository
                    .findById(answer.getQuestionId()).orElse(null);

            if (correctedAnswer == null) {
                return null;
            }

            BigDecimal questionPoints = BigDecimal.valueOf(correctedAnswer.getQuestion().getPoints());

            double answerPoints = answer.getTextAnswer().
                    equalsIgnoreCase(correctedAnswer.getAnswerText()) ? questionPoints.doubleValue() : 0;

            StudentAnswer studentAnswer = studentAnswerRepository
                    .findByExamAttemptIdAndQuestionId(attemptId, answer.getQuestionId());


            return new StudentAnswer(
                    studentAnswer != null ? studentAnswer.getId() : 0,
                    Collections.singletonList(correctedAnswer),
                    attempt,
                    correctedAnswer.getQuestion(),
                    answerPoints
            );
        }).filter(Objects::nonNull).collect(Collectors.toList());
        studentAnswerRepository.saveAll(studentAnswers);
    }


    // Get all student answers of the exam: Done
    public List<StudentAnswer> getAllAnswers(long attemptId){
        return this.studentAnswerRepository.findAllByExamAttemptId(attemptId);
    }



}