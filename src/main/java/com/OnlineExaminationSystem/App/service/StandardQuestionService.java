package com.OnlineExaminationSystem.App.service;

import com.OnlineExaminationSystem.App.entity.Exam.Exam;
import com.OnlineExaminationSystem.App.entity.Exam.questions.QuestionType;
import com.OnlineExaminationSystem.App.entity.Exam.questions.codeQuestion.CodeQuestion;
import com.OnlineExaminationSystem.App.entity.Exam.questions.standardQuestion.StandardQuestion;
import com.OnlineExaminationSystem.App.entity.Exam.questions.standardQuestion.StandardQuestionAnswer;
import com.OnlineExaminationSystem.App.repository.ExamRepository;
import com.OnlineExaminationSystem.App.repository.questionRepo.QuestionAnswerRepository;
import com.OnlineExaminationSystem.App.repository.questionRepo.standardQuestionRepo.StandardQuestionRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class StandardQuestionService {


    @Autowired
    private ExamRepository examRepository;

    @Autowired
    private StandardQuestionRepository standardQuestionRepository;

    @Autowired
    private QuestionAnswerRepository questionAnswerRepository;


    public List<StandardQuestion> saveQuestions(List<StandardQuestion> standardQuestions, Long examId) {
        Exam exam = this.examRepository.findExamById(examId).get();
        exam.setStandardQuestions(standardQuestions);

        standardQuestions.forEach(question -> {
            if (!question.getQuestionType().name().equalsIgnoreCase(QuestionType.CODING.name())) {
                question.setExam(exam);
                question.getQuestionAnswers().forEach(answer -> answer.setQuestion(question));
            }
        });
        return this.standardQuestionRepository.saveAll(standardQuestions);
    }


    public List<StandardQuestion> getStandardQuestions(long examId) {
        return this.standardQuestionRepository.findAllByExamId(examId);
    }
    public void deleteStandardQuestion(StandardQuestion standardQuestion){
        this.standardQuestionRepository.delete(standardQuestion);
    }

    public void deleteStandardQuestionAnswer(List<StandardQuestionAnswer> questionAnswers){
        this.questionAnswerRepository.deleteAll(questionAnswers);
    }

}
