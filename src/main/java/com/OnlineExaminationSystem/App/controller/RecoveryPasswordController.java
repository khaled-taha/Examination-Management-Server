package com.OnlineExaminationSystem.App.controller;

import com.OnlineExaminationSystem.App.exceptions.ApiException;
import com.OnlineExaminationSystem.App.service.RecoveryPasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/recovery")
public class RecoveryPasswordController {

    @Autowired
    private RecoveryPasswordService recoveryPasswordService;

    @PostMapping("/sendEmail/{email}")
    public ResponseEntity<Void> sendEmail(@PathVariable("email") String email){
        try {
            this.recoveryPasswordService.checkEmail(email);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e){
            throw new ApiException(e.getMessage());
        }
    }

    @PutMapping("/reset/{userId}/{password}")
    public ResponseEntity<Void> resetPassword(
            @PathVariable("userId") long userId,
            @PathVariable("newPassword") String newPassword)
    {
        try {
            this.recoveryPasswordService.resetPassword(userId, newPassword);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e){
            throw new ApiException(e.getMessage());
        }
    }
}
