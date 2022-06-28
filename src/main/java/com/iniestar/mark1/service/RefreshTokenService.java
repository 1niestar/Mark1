package com.iniestar.mark1.service;

import com.iniestar.mark1.db.entity.RefreshToken;
import com.iniestar.mark1.db.repo.RefreshTokenRepository;
import com.iniestar.mark1.utils.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class RefreshTokenService {

    @Autowired
    RefreshTokenRepository refreshTokenRepository;

    public RefreshToken generateRefreshToken(String uri) {
        String token = UUID.randomUUID().toString();

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUri(uri);
        refreshToken.setToken(token);
        refreshToken.setExpiryDate(Tool.getTimestampForDays(30).toLocalDateTime());
        refreshTokenRepository.save(refreshToken);

        return refreshToken;
    }

    public boolean isExpiredRefreshToken(String token) {

        RefreshToken refreshToken = refreshTokenRepository.findOneByToken(token);
        if(LocalDateTime.now().compareTo(refreshToken.getExpiryDate()) < 0) {
            return false;
        }

        return true;
    }

}
