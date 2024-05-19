package com.example.userService.controllers;

import com.example.userService.dto.*;
import com.example.userService.models.Session;
import com.example.userService.models.SessionStatus;
import com.example.userService.repositories.SessionRepository;
import com.example.userService.services.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private AuthService authService;
    //private final SessionRepository sessionRepository;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody LoginRequestDto request){
        return authService.login(request.getEmail(),request.getPassword());
    }

    @PostMapping("/logout")
    public ResponseEntity<Session> logout(@RequestBody LogoutRequesrDto request){
        authService.logout(request.getToken(),request.getUserId());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/signup")
    public ResponseEntity<UserDto> signUp(@RequestBody SignUpRequestDto request){
        UserDto userDto=authService.signUp(request.getEmail(),request.getPassword());
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @PostMapping("/validate")
    public ResponseEntity<SessionStatus> validateToken(@RequestBody ValidateTokenRequestDto request){
        SessionStatus sessionStatus=authService.validateSession(request.getToken(), request.getUserId());
        return new ResponseEntity<>(sessionStatus,HttpStatus.OK);
    }

}
