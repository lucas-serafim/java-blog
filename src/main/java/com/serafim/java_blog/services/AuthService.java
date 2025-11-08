package com.serafim.java_blog.services;

import com.serafim.java_blog.domain.User;
import com.serafim.java_blog.dto.AuthRequestDTO;
import com.serafim.java_blog.dto.AuthResponseDTO;
import com.serafim.java_blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private JwtEncoder jwtEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public AuthResponseDTO login(AuthRequestDTO authRequestDTO) {
        Optional<User> user = userRepository.findByEmail(authRequestDTO.getEmail());

        if (user.isEmpty() || !passwordEncoder.matches(authRequestDTO.getPassword(), user.get().getPassword())) {
            throw new BadCredentialsException("user or password is invalid!");
        }

        var now = Instant.now();

        // TODO: Add user roles

        var claims = JwtClaimsSet.builder()
                .issuer("auth-java-blog")
                .subject(user.get().getId())
                .expiresAt(generateExpirationDate())
                .build();

        String jwtToken =  jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

        return new AuthResponseDTO(jwtToken);
    }

    private Instant generateExpirationDate() {
        return LocalDateTime.now().plusHours(1).toInstant(ZoneOffset.of("-03:00"));
    }
}