package com.example.java_version.filter;

import com.example.java_version.repository.SampleUserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Instant;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtDecoder decoder;

    private final SampleUserRepository userRepository;

    public JwtAuthFilter(JwtDecoder decoder, SampleUserRepository userRepository) {
        this.decoder = decoder;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Verifica se existe o Authorization no header, se não passa a request para os outros filters
        if (request.getHeader("Authorization") != null) {
            String token = request.getHeader("Authorization").replace("Bearer ", "");

            UserDetails user = verifyIfTokenIsValid(token);

            // Cria um objeto com "intenção" de autenticação
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

            // Passa pro SecurityContextHolder
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        filterChain.doFilter(request, response);
    }

    private UserDetails verifyIfTokenIsValid(String token) {
        // Extrai o token e verifica se foi assinado com a chave correta
        var jwt = decoder.decode(token);

        Instant now = Instant.now();

        // Verifica se não expirou
        if (now.isAfter(jwt.getExpiresAt())) throw new AccessDeniedException("Token expirado");

        var subject = jwt.getSubject();

        // Verifica se o usuário existe na base de dados e retorna caso exista
        return userRepository.findUserByEmail(subject).orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado!"));
    }
}
