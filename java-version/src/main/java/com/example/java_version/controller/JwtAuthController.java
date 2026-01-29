package com.example.java_version.controller;

import com.example.java_version.dto.LoginJwtDTO;
import com.example.java_version.dto.TokenResponseDTO;
import com.example.java_version.service.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/jwt")
public class JwtAuthController {

    private final JwtService jwtService;

    public JwtAuthController(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDTO> login(@RequestBody LoginJwtDTO dto) {
        String token = jwtService.generateToken(dto.email(), dto.password());
        return ResponseEntity.ok().body(new TokenResponseDTO(token));
    }
}
