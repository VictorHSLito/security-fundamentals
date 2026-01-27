package com.example.kotlin_version.exceptions

data class EmailAlreadyExistsException(
    override val message: String
): RuntimeException(message)