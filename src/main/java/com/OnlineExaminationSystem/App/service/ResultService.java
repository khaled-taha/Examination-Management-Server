package com.OnlineExaminationSystem.App.service;

import com.OnlineExaminationSystem.App.entity.Exam.ExamAttempt;
import com.OnlineExaminationSystem.App.entity.Exam.ExamResult;
import com.OnlineExaminationSystem.App.entity.Exam.Group;
import com.OnlineExaminationSystem.App.entity.Exam.StudentAnswer;
import com.OnlineExaminationSystem.App.entity.dto.studentAnswer.ExamResultDto;
import com.OnlineExaminationSystem.App.repository.ExamAttemptRepository;
import com.OnlineExaminationSystem.App.repository.ExamResultRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
public class ResultService {

    @Autowired
    private ExamResultRepository examResultRepository;

    @Autowired
    private ExamAttemptRepository attemptRepository;

    @Autowired
    private ExamService examService;

    @Autowired
    private StudentAnswerService studentAnswerService;

    @Autowired
    public GroupService groupService;

    @Transactional
    public ExamResultDto createResult(long attemptId){
        // get exam attempt
        Optional<ExamAttempt> attempt = this.attemptRepository.findById(attemptId);

        double points = this.examService.getExamPoints(attempt.get().getExam().getId());
        double score = this.calculateResult(attemptId, points);


        // create the result
        ExamResult result = new ExamResult();
        result.setScore(score);
        result.setPassed(result.getScore() >= attempt.get().getExam().getSuccessRate());
        result.setExamAttempt(attempt.get());

        ExamResult examResult =  this.examResultRepository.save(result);

        return ExamResultDto.builder()
                .id(examResult.getId())
                .score(examResult.getScore())
                .passed(examResult.isPassed())
                .dateTime(examResult.getExamAttempt().getEndTime()
                        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm a")))
                .name(examResult.getExamAttempt().getUser().getFirstName() +
                        " " + examResult.getExamAttempt().getUser().getLastName())
                .groupName(examResult.getExamAttempt().getExam().getCourse().getGroup().getName())
                .build();
    }

    // get the result of the student
    public ExamResult getResult(long examAttemptId){
        return this.examResultRepository.getExamResultByExamAttemptId(examAttemptId);
    }

    // calculate the result
    private double calculateResult(long attemptId, double examPoints){
        // get all student answers
        List<StudentAnswer> answers = this.studentAnswerService.getAllAnswers(attemptId);

        // get the student score
        double score = answers.stream().mapToDouble(StudentAnswer::getPoints).sum();

        // get final score
        BigDecimal examResult = BigDecimal.valueOf(score).
                divide(BigDecimal.valueOf(examPoints), 3, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100));

        return examResult.doubleValue();
    }

    public List<ExamResultDto> findAllUsersAttemptedExam(long examId){
        List<ExamResult> examResults = this.examResultRepository.findAllByExamId(examId);
        List<ExamResultDto> results = new ArrayList<>();

        examResults.stream().forEach((result) -> {

            results.add(

                    ExamResultDto.builder()
                    .id(result.getId())
                    .score(result.getScore())
                    .passed(result.isPassed())
                    .dateTime(result.getExamAttempt().getEndTime()
                            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm a")))
                    .name(result.getExamAttempt().getUser().getFirstName() +
                            " " + result.getExamAttempt().getUser().getLastName())
                    .build()
            );
        });
        return results;
    }



}
