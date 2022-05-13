package com.tests.security.Services;

import com.tests.security.Beans.request.RefreshTokenRequest;
import com.tests.security.Beans.response.RefreshTokenResponse;
import com.tests.security.Entities.RefreshTokenEntity;
import com.tests.security.Exceptions.RefreshTokenException;
import com.tests.security.Repositories.RefreshTokenRepository;
import com.tests.security.Repositories.UserRepository;
import com.tests.security.access.JwtTokenProvider;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshService {

    @Getter
    @Value("${auth.jwt.expiration-refresh}")
    private Long refreshExpiration;

    @Autowired
    RefreshTokenRepository refreshTokenRepository;

    @Autowired
    UserRepository userRepository;
    @Autowired
    JwtTokenProvider jwtTokenProvider;

    private Optional<RefreshTokenEntity> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshTokenEntity createRefreshToken(Long userId){
        RefreshTokenEntity refreshToken = new RefreshTokenEntity();
        refreshToken.setUser(userRepository.findById(userId).get());
        refreshToken.setExpirationDate(Instant.now().plusMillis(getRefreshExpiration()));
        refreshToken.setToken(UUID.randomUUID().toString());
        return refreshTokenRepository.save(refreshToken);
    }

    private RefreshTokenEntity checkRefreshTokenExpiration(RefreshTokenEntity refreshToken) { //Мб есть встроенные методы?
        if(refreshToken.getExpirationDate().compareTo(Instant.now()) < 0){
            refreshTokenRepository.delete(refreshToken);
            throw new RefreshTokenException(refreshToken.getToken(),"Время действия токена истекло. Войдите заново");
        }
        return refreshToken;
    }

    public ResponseEntity<?> newAuthToken (RefreshTokenRequest refreshTokenRequest){
        String requestRefreshToken = refreshTokenRequest.getRefreshToken();
        return findByToken(requestRefreshToken)
                .map(this::checkRefreshTokenExpiration)
                .map(RefreshTokenEntity::getUser)
                .map(user -> {
                    String token = jwtTokenProvider.createTokenFromUsername(user.getUsername());
                    return ResponseEntity.ok(new RefreshTokenResponse(token, requestRefreshToken));
                })
                .orElseThrow(() -> new RefreshTokenException(requestRefreshToken,
                        "Такого токена нет!"));

    }

    public  ResponseEntity<?> deleteToken (RefreshTokenRequest refreshTokenRequest){
        String refreshToken = refreshTokenRequest.getRefreshToken();
        System.out.println(findByToken(refreshToken).map(RefreshTokenEntity::getToken).get() + "deleted");
        refreshTokenRepository.deleteByToken(findByToken(refreshToken).map(RefreshTokenEntity::getToken).get());
        return ResponseEntity.ok("Токен стерт" + refreshToken);
    }

}
