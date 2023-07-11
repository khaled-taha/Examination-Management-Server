package com.OnlineExaminationSystem.App.entity.Exam.questions.codeQuestion;

import com.OnlineExaminationSystem.App.entity.Exam.questions.Question;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;

@Entity
@Table(name = "code_problem", schema = "public")
@NoArgsConstructor
@Setter
@Getter
@DiscriminatorValue("codeQuestion")
public class CodeQuestion extends Question {

    @Column(name = "header")
    private String header;

    @Column(name = "timeLimit")
    private int timeLimit;

    @OneToMany(mappedBy = "codeProblem", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<TestCase> testCases;
}