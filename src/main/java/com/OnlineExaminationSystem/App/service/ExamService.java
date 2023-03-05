package com.OnlineExaminationSystem.App.service;

import com.OnlineExaminationSystem.App.entity.Exam.Exam;
import com.OnlineExaminationSystem.App.entity.Exam.Question;
import com.OnlineExaminationSystem.App.entity.Exam.QuestionType;
import com.OnlineExaminationSystem.App.entity.dto.ExamDto;
import com.OnlineExaminationSystem.App.entity.dto.QuestionAnswerDto;
import com.OnlineExaminationSystem.App.entity.dto.QuestionDto;
import com.OnlineExaminationSystem.App.exceptions.ApiException;
import com.OnlineExaminationSystem.App.repository.ExamRepository;
import com.OnlineExaminationSystem.App.repository.QuestionRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public Exam saveExam(Exam exam){
        return this.examRepository.save(exam);
    }

    public Exam getExamById(long id){
        return this.examRepository.findExamById(id)
                .orElseThrow(() -> new ApiException("Exam Not found"));
    }

    public void deleteExam(Exam exam){
        this.questionRepository.deleteAllByExamId(exam.getId());
        this.examRepository.delete(exam);
    }


    public List<Question> saveQuestions(List<Question> questions) {

        Exam exam = questions.get(0).getExam();
        exam.setQuestions(questions);

        questions.stream().forEach(question ->
                question.getQuestionAnswers().stream().forEach(answer -> answer.setQuestion(question))
        );

        return this.examRepository.save(exam).getQuestions();
    }

    public List<Question> getExamQuestions(long examId) {
        return this.questionRepository.findAllByExamId(examId);
    }

    public void deleteQuestion(Question question){
        this.questionRepository.delete(question);
    }


    public List<Exam> getAllExams() {
       return this.examRepository.findAll();
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

                questionAnswerDto.add(QuestionAnswerDto.builder()
                        .id(answer.getId())
                        .answerText((answer.getQuestion().getQuestionType() != QuestionType.Matching)
                                ? answer.getAnswerText() : null)
                        .build());
            });

            // set questions
            questionDto.add(QuestionDto.builder()
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
}
