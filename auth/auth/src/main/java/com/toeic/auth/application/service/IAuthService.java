package com.toeic.auth.application.service;

import com.toeic.auth.application.dto.AuthResponseDto;

public interface IAuthService {
    AuthResponseDto authenticate(String username, String password);
    AuthResponseDto refreshToken(String refreshToken);
    boolean validateToken(String token);
    boolean logout(String refreshToken);
}
