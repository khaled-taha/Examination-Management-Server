package com.OnlineExaminationSystem.App.service;

import com.OnlineExaminationSystem.App.entity.Exam.Exam;
import com.OnlineExaminationSystem.App.entity.Exam.Question;
import com.OnlineExaminationSystem.App.exceptions.ApiException;
import com.OnlineExaminationSystem.App.repository.ExamRepository;
import com.OnlineExaminationSystem.App.repository.QuestionRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
