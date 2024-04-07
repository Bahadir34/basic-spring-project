package com.example.demo.payload.validator;

import com.example.demo.exception.exceptions.ConflictException;
import com.example.demo.messages.ExceptionMessages;
import com.example.demo.payload.request.RegisterRequest;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RegisterUniqueFieldsValidator {

    private final UserRepository userRepository;

    @Autowired
    public RegisterUniqueFieldsValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean registerRequestValidator(RegisterRequest registerRequest) {

        List<String> emails = userRepository.getAllEmails();
        List<String> userNames = userRepository.getAllUserNames();

        for (String email : emails) {
            if (email.contains(registerRequest.getEmail())) {
                return true;
            }
        }

        for (String userName : userNames) {
            if (userName.contains(registerRequest.getUserName())) {
                return true;
            }
        }


        return false;
    }

}
