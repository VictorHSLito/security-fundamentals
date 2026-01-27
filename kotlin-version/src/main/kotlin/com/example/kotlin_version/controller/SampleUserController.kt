package com.example.kotlin_version.controller

import com.example.kotlin_version.dto.CreateUserDTO
import com.example.kotlin_version.dto.UserCreatedDTO
import com.example.kotlin_version.service.SampleUserService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user")
class SampleUserController (
    private val userService: SampleUserService
){
    @PostMapping
    fun createUser(@RequestBody @Valid dto: CreateUserDTO): ResponseEntity<UserCreatedDTO> {
        val response = userService.create(dto)
        return ResponseEntity.ok().body(response);
    }
}