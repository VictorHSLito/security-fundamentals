package com.example.kotlin_version.repository

import com.example.kotlin_version.entity.SampleUser
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SampleUserRepository: JpaRepository<SampleUser, Long> {
    fun findUserByEmail(email: String): SampleUser?
}