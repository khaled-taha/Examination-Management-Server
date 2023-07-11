package com.OnlineExaminationSystem.App.entity.Exam.questions.standardQuestion;

import com.OnlineExaminationSystem.App.entity.Exam.questions.Question;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "standard_question", schema = "public")
@NoArgsConstructor
@Setter
@Getter
@DiscriminatorValue("standardQuestion")
public class StandardQuestion extends Question implements Serializable {

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "standard_question_standard_answer",
            joinColumns = @JoinColumn(name = "question_id"),
            inverseJoinColumns = @JoinColumn(name = "standard_answer_id"))
    private List<StandardQuestionAnswer> questionAnswers = new ArrayList<>();


}
