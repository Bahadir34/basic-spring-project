package com.example.demo.payload.request;

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
public class LoginRequest {

    @NotEmpty(message = "User name must not be empty!")
    @NotNull(message = "User name must not be null!")
    private String userName;

    @NotEmpty(message = "User name must not be empty!")
    @NotNull(message = "User name must not be null!")
    private String password;

}
