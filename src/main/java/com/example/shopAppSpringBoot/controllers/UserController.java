package com.example.shopAppSpringBoot.controllers;

import com.example.shopAppSpringBoot.dtos.UserDTO;
import com.example.shopAppSpringBoot.dtos.UserLoginDTO;
import com.example.shopAppSpringBoot.responses.LoginResponse;
import com.example.shopAppSpringBoot.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/user")
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> createUser(@RequestBody UserDTO userDTO){
        try {

            return ResponseEntity.ok(userService.createUser(userDTO));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @Valid @RequestBody UserLoginDTO userLoginDTO
    ){
        try {
            String token = userService.login(userLoginDTO.getPhoneNumber(), userLoginDTO.getPassword());
            LoginResponse loginResponse = LoginResponse.builder()
                    .message("login successfully")
                    .token(token)
                    .build();
            return ResponseEntity.ok(loginResponse);
        }catch (Exception e){
            LoginResponse loginResponse = LoginResponse.builder()
                    .message("login fail with " + e.getMessage())
                    .build();
            return ResponseEntity.badRequest().body(loginResponse);
        }

    }
}
