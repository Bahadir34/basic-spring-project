package com.example.demo.service;

import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.entity.enums.Roles;
import com.example.demo.exception.exceptions.ConflictException;
import com.example.demo.exception.exceptions.ResourceNotFoundException;
import com.example.demo.messages.ExceptionMessages;
import com.example.demo.payload.mapper.RegisterMapper;
import com.example.demo.payload.request.LoginRequest;
import com.example.demo.payload.request.RegisterRequest;
import com.example.demo.payload.response.AuthResponse;
import com.example.demo.payload.response.RegisterResponse;
import com.example.demo.payload.validator.RegisterUniqueFieldsValidator;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.jwt.JwtUtils;
import com.example.demo.security.service.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthenticationService {

    private final RegisterMapper registerMapper;
    private final UserRepository userRepository;
    private final RegisterUniqueFieldsValidator registerUniqueFieldsValidator;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    @Autowired
    public AuthenticationService(RegisterMapper registerMapper,
                                 UserRepository userRepository,
                                 RegisterUniqueFieldsValidator registerUniqueFieldsValidator,
                                 AuthenticationManager authenticationManager,
                                 JwtUtils jwtUtils,
                                 PasswordEncoder passwordEncoder,
                                 RoleService roleService) {
        this.registerMapper = registerMapper;
        this.userRepository = userRepository;
        this.registerUniqueFieldsValidator = registerUniqueFieldsValidator;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }

    public RegisterResponse register(RegisterRequest registerRequest) {

        if (registerUniqueFieldsValidator.registerRequestValidator(registerRequest)) {
            throw new ConflictException(ExceptionMessages.CONFLICT_USER_ALREADY_REGISTERED);
        }

        User user = registerMapper.registerRequestToUser(registerRequest);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Set<Role> roles = new HashSet<>();
        roles.add(roleService.findRoleEquels(Roles.CUSTOMER));
        user.setRoles(roles);
        User savedUser = userRepository.save(user);
        RegisterResponse registerResponse = registerMapper.userToRegisterResponse(savedUser);

        return registerResponse;
    }

    public AuthResponse login(LoginRequest loginRequest) {

        try{
            String userName = loginRequest.getUserName();
            String password = loginRequest.getPassword();

            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName,password));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String token = "Bearer " + jwtUtils.generateJwtToken(authentication);
            //System.out.println("JWT Token : " + token);
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

            Set<String> roleSet = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet());

            AuthResponse authResponse = AuthResponse.builder()
                    .token(token)
                    .role(String.join(" " , roleSet))
                    .build();
            //System.out.println(authResponse.toString());
            return authResponse;

        }catch(RuntimeException e){
            System.out.println(e);
            throw new ResourceNotFoundException(ExceptionMessages.LOGIN_EXCEPTION);
        }
    }
}
