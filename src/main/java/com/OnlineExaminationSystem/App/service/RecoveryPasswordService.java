package com.OnlineExaminationSystem.App.service;

import com.OnlineExaminationSystem.App.entity.users.User;
import com.OnlineExaminationSystem.App.exceptions.ApiException;
import com.OnlineExaminationSystem.App.repository.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RecoveryPasswordService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private UserRepository userRepository;

    public void checkEmail(String email){
        Optional<User> user = this.userRepository.findUserByEmail(email);
        if(user.isEmpty())
            throw new ApiException("Email Not found");
        this.sendEmail(email);
    }

    private void sendEmail(String mail){
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom("engineersoftware515@gmail.com");
            helper.setTo(mail);
            helper.setSubject("Recovery Password");
            helper.setText(getIndexHtmlContent(), true);
            javaMailSender.send(message);
        } catch (MessagingException | IOException e){
            throw new ApiException(e.getMessage());
        }

    }

    public void resetPassword(long userId, String newPassword){
        Optional<User> user = this.userRepository.findById(userId);
        if(user.isEmpty())
            throw new ApiException("User Not found");
        user.get().setPassword(newPassword);
    }

    private String getIndexHtmlContent() throws IOException {
        File file = new File("index.html");
        return Files.readString(file.toPath());
    }


}
