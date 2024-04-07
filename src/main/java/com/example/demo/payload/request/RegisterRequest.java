package com.example.demo.payload.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class RegisterRequest {

    @NotNull(message = "Please enter your first name!")
    @NotEmpty(message = "Please enter your first name!")
    private String firstName;

    @NotNull(message = "Please enter your last name!")
    @NotEmpty(message = "Please enter your last name!")
    private String lastName;

    @NotNull(message = "Please enter your user name!")
    @NotEmpty(message = "Please enter your user name!")
    private String userName;

    @NotNull(message = "Please enter your email!")
    @NotEmpty(message = "Please enter your email!")
    @Email(message = "Please enter a valid email!")
    private String email;

    @NotNull(message = "Please enter your password!")
    @NotEmpty(message = "Please enter your password!")
    private String password;
}
