package com.OnlineExaminationSystem.App.controller;

import com.OnlineExaminationSystem.App.entity.dto.AuthenticationRequest;
import com.OnlineExaminationSystem.App.entity.dto.AuthenticationResponse;
import com.OnlineExaminationSystem.App.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest request){
        return new ResponseEntity<>(this.authService.auth(request), HttpStatus.OK);
    }

}