package com.example.demo.payload.mapper;

import com.example.demo.entity.User;
import com.example.demo.payload.request.RegisterRequest;
import com.example.demo.payload.response.RegisterResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RegisterMapper {

    RegisterResponse userToRegisterResponse(User user);

    User registerRequestToUser(RegisterRequest registerRequest);
}
