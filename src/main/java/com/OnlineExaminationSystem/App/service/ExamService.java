package com.OnlineExaminationSystem.App.service;

import com.OnlineExaminationSystem.App.entity.Exam.*;
import com.OnlineExaminationSystem.App.entity.Exam.questions.Question;
import com.OnlineExaminationSystem.App.entity.Exam.questions.codeQuestion.CodeQuestion;
import com.OnlineExaminationSystem.App.entity.Exam.questions.standardQuestion.StandardQuestionAnswer;
import com.OnlineExaminationSystem.App.entity.Exam.questions.QuestionType;
import com.OnlineExaminationSystem.App.entity.Exam.questions.standardQuestion.StandardQuestion;
import com.OnlineExaminationSystem.App.entity.dto.exam.*;
import com.OnlineExaminationSystem.App.entity.users.User;
import com.OnlineExaminationSystem.App.exceptions.ApiException;
import com.OnlineExaminationSystem.App.repository.*;
import com.OnlineExaminationSystem.App.repository.questionRepo.QuestionAnswerRepository;
import com.OnlineExaminationSystem.App.repository.questionRepo.standardQuestionRepo.StandardQuestionRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
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
    private StandardQuestionRepository standardQuestionRepository;

    @Autowired
    private ExamAttemptRepository attemptRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QuestionAnswerRepository questionAnswerRepository;

    public Exam saveExam(Exam exam){
        return this.examRepository.save(exam);
    }
    public List<Exam> getAllExams() {
        List<Exam> exams = this.examRepository.findAll();
        System.out.println("sdcsflv,plsdf,vmsdfomkvolf");
        exams.forEach((exam) -> {
            System.out.println(exam.getExamName());
            if(LocalDateTime.now(ZoneId.of("Africa/Cairo")).compareTo(getTime(exam.getStartTime())) >= 0
                    && LocalDateTime.now(ZoneId.of("Africa/Cairo")).compareTo(getTime(exam.getEndTime())) < 0) {
                exam.setState(true);
            }
            else
                exam.setState(false);
        });
        this.examRepository.saveAll(exams);
        return exams;
    }
    private LocalDateTime getTime(LocalDateTime time){
        return LocalDateTime.of(time.getYear(), time.getMonthValue(), time.getDayOfMonth(),
                time.getHour(), time.getMinute());
    }
    public Exam getExamById(long id){
        Exam exam =  this.examRepository.findExamById(id)
                .orElseThrow(() -> new ApiException("Exam Not found"));

        if(LocalDateTime.now(ZoneId.of("Africa/Cairo")).compareTo(getTime(exam.getStartTime())) >= 0
                && LocalDateTime.now(ZoneId.of("Africa/Cairo")).compareTo(getTime(exam.getEndTime())) < 0)
            exam.setState(true);
        else
            exam.setState(false);
        this.examRepository.save(exam);
        return exam;
    }
    public void deleteExam(Exam exam){
        this.standardQuestionRepository.deleteAllByExamId(exam.getId());
        this.examRepository.delete(exam);
    }
    public void deleteExam(long id){
        this.examRepository.deleteById(id);
    }
    public ExamAttemptDto attemptExam(long userId, long examId){

        List<ExamAttempt> attempt = this.attemptRepository.getExamAttemptByUserId(userId).stream()
                .filter(examAttempt -> examAttempt.getExam().getId().equals(examId)).toList();

        if(attempt.size() > 0 && (attempt.get(0).getStartTime() != null && attempt.get(0).getEndTime() == null)){
            Optional<Exam> exam =  this.examRepository.findExamById(examId);
            Optional<User> user = this.userRepository.findById(userId);

            ExamAttempt examAttempt = attempt.get(0);
            examAttempt.setUser(user.get());
            examAttempt.setExam(exam.get());
            return ExamAttemptDto.mapToExamAttemptDto(examAttempt);
        }

        if(attempt.size() > 0 && attempt.get(0).getEndTime() != null){
            throw new ApiException("You attempted this exam At " + attempt.get(0).getStartTime()
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm a")));
        }

        Optional<Exam> exam =  this.examRepository.findExamById(examId);
        Optional<User> user = this.userRepository.findById(userId);


        if(user.isPresent() && exam.isPresent()
                && (LocalDateTime.now(ZoneId.of("Africa/Cairo")).compareTo(getTime(exam.get().getStartTime())) >= 0
                && LocalDateTime.now(ZoneId.of("Africa/Cairo")).compareTo(getTime(exam.get().getEndTime())) < 0)) {

            ExamAttempt examAttempt = new ExamAttempt();
            examAttempt.setUser(user.get());
            examAttempt.setExam(exam.get());
            examAttempt =  this.attemptRepository.save(examAttempt);
            return ExamAttemptDto.mapToExamAttemptDto(examAttempt);
        }
        throw new ApiException("Expired Exam");
    }

    public ExamAttemptDto testExam(long userId, long examId){

        Optional<Exam> exam =  this.examRepository.findExamById(examId);
        Optional<User> user = this.userRepository.findById(userId);


        if(user.isPresent() && exam.isPresent()
                && (LocalDateTime.now(ZoneId.of("Africa/Cairo")).compareTo(getTime(exam.get().getStartTime())) >= 0
                && LocalDateTime.now(ZoneId.of("Africa/Cairo")).compareTo(getTime(exam.get().getEndTime())) < 0)) {

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
            attempt.get().setEndTime(LocalDateTime.now(ZoneId.of("Africa/Cairo")));
            this.attemptRepository.save(attempt.get());
        }
    }

    public int getExamPoints(long examId){
        //get the exam
        Optional<Exam> exam = this.examRepository.findExamById(examId);

        // get all questions with answers
        List<Question> questions = new ArrayList<>();
        questions.addAll(exam.get().getStandardQuestions());
        questions.addAll(exam.get().getCodeProblems());

        // get the total points of the exam
        return questions.stream().mapToInt(Question::getPoints).sum();
    }
    public ExamDto renderExam(long examId) {

        Exam exam = this.getExamById(examId);

        List<StandardQuestion> standardQuestions = exam.getStandardQuestions();
        List<CodeQuestion> codeQuestions = exam.getCodeProblems();


        List<StandardQuestionDto> standardQuestionDtos = new ArrayList<>();
        List<CodeQuestionDto> codeQuestionDtos = new ArrayList<>();

        standardQuestions.forEach((question) -> {

            // set question answers
            List<QuestionAnswerDto> questionAnswerDtos = new ArrayList<>();

            question.getQuestionAnswers().stream().forEach((answer) -> {
                questionAnswerDtos.add(QuestionAnswerDto.mapToQuestionAnswerDto(answer));
            });

            // set questions
            standardQuestionDtos.add(StandardQuestionDto.mapToQuestionDto(question, questionAnswerDtos));
        });

        codeQuestions.forEach((question) -> {
            // set questions
            codeQuestionDtos.add(CodeQuestionDto.mapToCodeQuestionDto(question));
        });

        return ExamDto.mapToExamDto(exam, standardQuestionDtos, codeQuestionDtos);
    }
    public List<Exam> getAllExamsByCourseId(Long courseId) {

        List<Exam> exams = examRepository.findAllExamsByCourseId(courseId);
        exams.stream().forEach((exam) -> {

            if(LocalDateTime.now(ZoneId.of("Africa/Cairo")).compareTo(getTime(exam.getStartTime())) >= 0
                    && LocalDateTime.now(ZoneId.of("Africa/Cairo")).compareTo(getTime(exam.getEndTime())) < 0)
                exam.setState(true);
            else
                exam.setState(false);
        });
        this.examRepository.saveAll(exams);

        return exams;
    }
    public List<ExamAttemptDto> getAllAttempts(Long userId){
        List<ExamAttempt> examAttempts =  this.attemptRepository.getAllExamAttemptByUserId(userId);
        return examAttempts.stream().map(ExamAttemptDto::mapToExamAttemptDto).collect(Collectors.toList());
    }
}
