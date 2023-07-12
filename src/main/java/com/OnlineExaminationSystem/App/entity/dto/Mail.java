package com.OnlineExaminationSystem.App.entity.dto;

import lombok.Data;

@Data
public class Mail {
    private String from;
    private String to;
    private String header;
    private String content;
}
