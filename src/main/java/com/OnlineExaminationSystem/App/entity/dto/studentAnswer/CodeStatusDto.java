package com.OnlineExaminationSystem.App.entity.dto.studentAnswer;

import lombok.Data;

@Data
public class CodeStatusDto {
    private short status;
    private String log;
    private String code;

    public CodeStatusDto() {
    }

    public CodeStatusDto(short status, String log, String code) {
        this.status = status;
        this.log = log;
        this.code = code;
    }
}
