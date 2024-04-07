package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.exception.exceptions.ConflictException;
import com.example.demo.messages.ExceptionMessages;
import com.example.demo.payload.mapper.RegisterMapper;
import com.example.demo.payload.request.RegisterRequest;
import com.example.demo.payload.response.RegisterResponse;
import com.example.demo.payload.response.ResponseMessage;
import com.example.demo.payload.validator.RegisterUniqueFieldsValidator;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final RegisterMapper registerMapper;
    private final UserRepository userRepository;
    private final RegisterUniqueFieldsValidator registerUniqueFieldsValidator;

    @Autowired
    public AuthenticationService(RegisterMapper registerMapper,
                                 UserRepository userRepository,
                                 RegisterUniqueFieldsValidator registerUniqueFieldsValidator) {
        this.registerMapper = registerMapper;
        this.userRepository = userRepository;
        this.registerUniqueFieldsValidator = registerUniqueFieldsValidator;
    }

    public RegisterResponse register(RegisterRequest registerRequest) {

        if (registerUniqueFieldsValidator.registerRequestValidator(registerRequest)) {
            throw new ConflictException(ExceptionMessages.CONFLICT_USER_ALREADY_REGISTERED);
        }

        User user = registerMapper.registerRequestToUser(registerRequest);
        User savedUser = userRepository.save(user);
        RegisterResponse registerResponse = registerMapper.userToRegisterResponse(savedUser);

        return registerResponse;
    }
}
