package com.example.java_version.repository;

import com.example.java_version.entity.SampleUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SampleUserRepository extends JpaRepository<SampleUser, Long> {
    Optional<SampleUser> findUserByEmail(String email);
}
