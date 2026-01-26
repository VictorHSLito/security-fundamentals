package com.example.kotlin_version

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KotlinVersionApplication

fun main(args: Array<String>) {
	runApplication<KotlinVersionApplication>(*args)
}
