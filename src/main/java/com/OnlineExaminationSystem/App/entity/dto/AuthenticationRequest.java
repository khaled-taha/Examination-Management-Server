package com.OnlineExaminationSystem.App.entity.dto;

import lombok.Data;

@Data
public class AuthenticationRequest {

    public String email;
    private String password;
}
