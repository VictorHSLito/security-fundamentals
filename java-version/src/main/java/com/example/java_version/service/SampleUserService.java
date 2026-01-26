package com.example.java_version.service;

import com.example.java_version.dto.CreateUserDTO;
import com.example.java_version.dto.UserCreatedDTO;
import com.example.java_version.entity.SampleUser;
import com.example.java_version.exception.EmailAlreadyExistsException;
import com.example.java_version.repository.SampleUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SampleUserService {

    private final SampleUserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public SampleUserService(SampleUserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserCreatedDTO create(CreateUserDTO dto) {
        if (userRepository.findUserByEmail(dto.email()).isPresent()) {
            throw new EmailAlreadyExistsException("Email já cadastrado na base de dados!");
        }

        String encryptedPassword = passwordEncoder.encode(dto.password());

        SampleUser user = SampleUser.builder()
                .username(dto.username())
                .email(dto.email())
                .password(encryptedPassword)
                .build();

        userRepository.save(user);

        return new UserCreatedDTO("Usuário criado com sucesso!");
    }
}
