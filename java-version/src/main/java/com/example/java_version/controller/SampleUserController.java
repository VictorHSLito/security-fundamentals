package com.example.java_version.controller;

import com.example.java_version.dto.CreateUserDTO;
import com.example.java_version.dto.UserCreatedDTO;
import com.example.java_version.service.SampleUserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class SampleUserController {

    private final SampleUserService userService;

    public SampleUserController(SampleUserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserCreatedDTO> createUser(@RequestBody @Valid CreateUserDTO dto) {
        UserCreatedDTO responseDto = userService.create(dto);
        return ResponseEntity.ok().body(responseDto);
    }
}
