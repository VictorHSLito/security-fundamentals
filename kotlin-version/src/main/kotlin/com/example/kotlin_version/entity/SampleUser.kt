package com.example.kotlin_version.entity

import jakarta.persistence.*

@Entity
@Table(name = "tb_user")
data class SampleUser(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long? = null,

    @Column(nullable = false)
    val username: String,

    @Column(unique = true, nullable = false)
    val email: String,

    @Column(nullable = false)
    val password: String
) {}
