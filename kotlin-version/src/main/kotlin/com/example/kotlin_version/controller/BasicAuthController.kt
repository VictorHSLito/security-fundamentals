package com.example.kotlin_version.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/basic")
class BasicAuthController {

    @PostMapping("/login")
    fun login(): ResponseEntity<Void> {
        return ResponseEntity.ok().build()
    }
}