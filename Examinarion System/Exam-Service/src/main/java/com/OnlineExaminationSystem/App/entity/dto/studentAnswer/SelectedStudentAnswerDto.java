package com.OnlineExaminationSystem.App.entity.dto.studentAnswer;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class SelectedStudentAnswerDto {

    @JsonProperty("questionId")
    private long questionId;
    @JsonProperty("answersIds")
    private List<Long> answersIds;

}
