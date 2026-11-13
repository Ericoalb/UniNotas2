package br.com.edge.UniNotas.service;

import br.com.edge.UniNotas.database.model.RefreshTokenEntity;
import br.com.edge.UniNotas.database.model.StudentEntity;
import br.com.edge.UniNotas.database.repository.RefreshTokenRepository;
import br.com.edge.UniNotas.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    @Value("${jwt.refresh-expiration:604800000}")
    private long refreshExpirationMs;

    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public RefreshTokenEntity createRefreshToken(StudentEntity student) {
        // Se o estudante já possui um refresh token, atualizamos o valor e a validade
        RefreshTokenEntity refreshToken = refreshTokenRepository.findByStudent(student)
                .orElse(RefreshTokenEntity.builder().student(student).build());

        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshExpirationMs));

        return refreshTokenRepository.save(refreshToken);
    }

    public Optional<RefreshTokenEntity> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    @Transactional
    public RefreshTokenEntity verifyExpiration(RefreshTokenEntity token) throws BadRequestException {
        if (token.getExpiryDate().isBefore(Instant.now())) {
            refreshTokenRepository.delete(token);
            throw new BadRequestException("Refresh token expirado. Por favor, realize um novo login.");
        }
        return token;
    }
}
