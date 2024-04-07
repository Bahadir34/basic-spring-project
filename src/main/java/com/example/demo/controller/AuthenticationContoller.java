package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.payload.request.RegisterRequest;
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
}
