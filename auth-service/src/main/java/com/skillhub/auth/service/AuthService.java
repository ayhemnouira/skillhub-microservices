
package com.skillhub.auth.service;

import com.skillhub.auth.dto.request.*;
import com.skillhub.auth.dto.response.AuthResponse;
import com.skillhub.auth.dto.response.MessageResponse;


public interface AuthService {
    MessageResponse register(RegisterRequest request);
    AuthResponse login(LoginRequest request);
    MessageResponse verifyEmail(VerifyEmailRequest request);
    MessageResponse forgotPassword(ForgotPasswordRequest request);
    MessageResponse resetPassword(ResetPasswordRequest request);
    AuthResponse refreshToken(RefreshTokenRequest request);
    MessageResponse logout(String userId);
    MessageResponse validateToken(String token);
}