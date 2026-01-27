package com.example.kotlin_version.service

import com.example.kotlin_version.repository.SampleUserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService(
    private val userRepository: SampleUserRepository
): UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        return userRepository.findUserByEmail(username) ?: throw UsernameNotFoundException("Usuário não encontrado!")
    }
}