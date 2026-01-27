package com.example.kotlin_version.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class ApiController {

    @GetMapping
    fun healthy(): ResponseEntity<Void> {
        return ResponseEntity.ok().build()
    }
}