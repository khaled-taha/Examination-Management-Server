package com.OnlineExaminationSystem.App.service;

import com.OnlineExaminationSystem.App.config.security.JwtService;
import com.OnlineExaminationSystem.App.entity.dto.AuthenticationRequest;
import com.OnlineExaminationSystem.App.entity.dto.AuthenticationResponse;
import com.OnlineExaminationSystem.App.entity.users.User;
import com.OnlineExaminationSystem.App.exceptions.ApiException;
import com.OnlineExaminationSystem.App.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;


    public AuthenticationResponse auth(AuthenticationRequest request){
        this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user = this.userRepository.findUserByEmail(request.getEmail())
                .orElseThrow(()-> new ApiException("User Not found"));

        String token = this.jwtService.generateToken(user);
        return new AuthenticationResponse(token);
    }
}