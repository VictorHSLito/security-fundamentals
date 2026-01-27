package com.example.kotlin_version.service

import com.example.kotlin_version.dto.CreateUserDTO
import com.example.kotlin_version.dto.UserCreatedDTO
import com.example.kotlin_version.entity.SampleUser
import com.example.kotlin_version.exceptions.EmailAlreadyExistsException
import com.example.kotlin_version.repository.SampleUserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class SampleUserService(
    private val userRepository: SampleUserRepository,
    private val passwordEncoder: PasswordEncoder
){
    fun create(dto: CreateUserDTO): UserCreatedDTO {

        userRepository.findUserByEmail(email = dto.email)?.let {
            throw EmailAlreadyExistsException(message = "Usuário com esse email já existe!")
        }

        val encryptedPassword = passwordEncoder.encode(dto.password)
            ?: throw RuntimeException("Houve um erro ao encriptar senha")

        val user = SampleUser(username = dto.username, password = encryptedPassword, email = dto.email)

        userRepository.save(user)

        return UserCreatedDTO(message = "Usuário criado com sucesso!")
    }

}