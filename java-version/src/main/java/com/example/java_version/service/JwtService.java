package com.example.java_version.service;

import com.example.java_version.entity.SampleUser;
import com.example.java_version.repository.SampleUserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class JwtService {

    @Value("${spring.security.jwt.issuer}")
    private String issuer;

    @Value("${spring.security.jwt.expiration-time}")
    private Long expiresAt;

    private final JwtEncoder encoder;

    private final PasswordEncoder passwordEncoder;

    private final SampleUserRepository userRepository;

    public JwtService(JwtEncoder encoder, PasswordEncoder passwordEncoder, SampleUserRepository userRepository) {
        this.encoder = encoder;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    public String generateToken(String email, String password) {

        SampleUser user = verifyUserCredentials(email, password);

        SignatureAlgorithm algorithm = SignatureAlgorithm.RS256;

        JwsHeader header = JwsHeader.with(algorithm).type("JWT").build();

        Instant now = Instant.now();

        JwtClaimsSet.Builder claimsSet = JwtClaimsSet.builder();

        claimsSet.subject(user.getUsername())
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiresAt))
                .issuer(issuer);

        JwtEncoderParameters parameters = JwtEncoderParameters.from(header, claimsSet.build());

        Jwt jwt = encoder.encode(parameters);

        return jwt.getTokenValue();
    }

    private SampleUser verifyUserCredentials(String email, String password) {
        SampleUser user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado!"));

        if (!passwordEncoder.matches(password, user.getPassword()))
            throw new AccessDeniedException("Credenciais inválidas!");

        return user;
    }
}
