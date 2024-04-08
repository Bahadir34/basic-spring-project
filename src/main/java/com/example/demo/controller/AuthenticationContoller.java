package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.messages.SuccessMessages;
import com.example.demo.payload.request.LoginRequest;
import com.example.demo.payload.request.RegisterRequest;
import com.example.demo.payload.response.AuthResponse;
import com.example.demo.payload.response.RegisterResponse;
import com.example.demo.payload.response.ResponseMessage;
import com.example.demo.service.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationContoller {

    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationContoller(AuthenticationService authenticationService){
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseMessage<RegisterResponse> register(@RequestBody @Valid RegisterRequest registerRequest){
        RegisterResponse registerResponse = authenticationService.register(registerRequest);

        return ResponseMessage.<RegisterResponse>builder()
                .object(registerResponse)
                .message("Kullanıcı kaydoldu!")
                .httpStatus(HttpStatus.CREATED)
                .build();
    }

    @PostMapping("/login")
    public ResponseMessage<AuthResponse> login(@RequestBody @Valid LoginRequest loginRequest){

        AuthResponse authResponse = authenticationService.login(loginRequest);

        return ResponseMessage.<AuthResponse>builder()
                .object(authResponse)
                .message(SuccessMessages.LOGIN_SUCCESS)
                .httpStatus(HttpStatus.OK)
                .build();
    }
}
