package com.example.kotlin_version.entity

import jakarta.persistence.*
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

@Entity
@Table(name = "tb_user")
class SampleUser(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private val id: Long? = null,

    @Column(nullable = false)
    private val username: String,

    @Column(unique = true, nullable = false)
    private val email: String,

    @Column(nullable = false)
    private val password: String
): UserDetails {
    override fun getAuthorities(): Collection<GrantedAuthority> =
        listOf(SimpleGrantedAuthority("USER"))

    override fun getPassword(): String = password

    override fun getUsername(): String = username
}
