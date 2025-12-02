package com.skillhub.auth.service;

import com.skillhub.auth.dto.request.*;
import com.skillhub.auth.dto.response.AuthResponse;
import com.skillhub.auth.dto.response.MessageResponse;
import com.skillhub.auth.entity.RefreshToken;
import com.skillhub.auth.entity.User;
import com.skillhub.auth.entity.VerificationToken;
import com.skillhub.auth.repository.RefreshTokenRepository;
import com.skillhub.auth.repository.UserRepository;
import com.skillhub.auth.repository.VerificationTokenRepository;
import com.skillhub.auth.security.JwtTokenProvider;
import com.skillhub.auth.util.OTPGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final VerificationTokenRepository tokenRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    private final EmailService emailService;

    @Override
    @Transactional
    public MessageResponse register(RegisterRequest request) {
        log.info("Registration attempt for email: {}", request.getEmail());

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already registered: " + request.getEmail());
        }

        String role = (request.getRole() != null && !request.getRole().isEmpty())
                ? request.getRole().toUpperCase()
                : "USER";

        Set<String> roles = new HashSet<>();
        roles.add(role);

        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(roles)
                .status("PENDING")
                .emailVerified(false)
                .accountLocked(false)
                .failedLoginAttempts(0)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        User savedUser = userRepository.save(user);
        log.info("User created successfully: {}", savedUser.getId());

        String otp = OTPGenerator.generate();
        log.debug("Generated OTP for user {}: {}", savedUser.getId(), otp);

        VerificationToken verificationToken = VerificationToken.builder()
                .userId(savedUser.getId())
                .token(otp)
                .type("EMAIL_VERIFICATION")
                .expiryDate(LocalDateTime.now().plusMinutes(10))
                .used(false)
                .createdAt(LocalDateTime.now())
                .build();

        tokenRepository.save(verificationToken);
        log.info("Verification token created for user: {}", savedUser.getId());

        emailService.sendVerificationEmail(savedUser.getEmail(), otp);

        return new MessageResponse(
                "Registration successful! Please check your email for verification code."
        );
    }

    @Override
    @Transactional
    public MessageResponse verifyEmail(VerifyEmailRequest request) {
        log.info("Email verification attempt for: {}", request.getEmail());

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getEmailVerified()) {
            throw new RuntimeException("Email already verified");
        }

        VerificationToken token = tokenRepository
                .findByTokenAndTypeAndUsedFalseAndExpiryDateAfter(
                        request.getOtp(),
                        "EMAIL_VERIFICATION",
                        LocalDateTime.now()
                )
                .orElseThrow(() -> new RuntimeException("Invalid or expired OTP"));

        if (!token.getUserId().equals(user.getId())) {
            throw new RuntimeException("Invalid OTP for this user");
        }

        user.setEmailVerified(true);
        user.setStatus("ACTIVE");
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);

        token.setUsed(true);
        tokenRepository.save(token);

        log.info("Email verified successfully for user: {}", user.getId());

        return new MessageResponse("Email verified successfully! You can now login.");
    }

    @Override
    @Transactional
    public AuthResponse login(LoginRequest request) {
        log.info("Login attempt for email: {}", request.getEmail());

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String accessToken = jwtTokenProvider.generateToken(authentication);

        String refreshTokenString = UUID.randomUUID().toString();

        RefreshToken refreshToken = RefreshToken.builder()
                .token(refreshTokenString)
                .userId(user.getId())
                .expiryDate(LocalDateTime.now().plusDays(7))
                .revoked(false)
                .createdAt(LocalDateTime.now())
                .build();

        refreshTokenRepository.save(refreshToken);

        user.setLastLogin(LocalDateTime.now());
        user.setFailedLoginAttempts(0);
        userRepository.save(user);

        log.info("Login successful for user: {}", user.getId());

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshTokenString)
                .tokenType("Bearer")
                .userId(user.getId())
                .email(user.getEmail())
                .roles(user.getRoles())
                .status(user.getStatus())
                .build();
    }

    @Override
    @Transactional
    public MessageResponse forgotPassword(ForgotPasswordRequest request) {
        log.info("Forgot password request for: {}", request.getEmail());

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String resetToken = UUID.randomUUID().toString();

        VerificationToken token = VerificationToken.builder()
                .userId(user.getId())
                .token(resetToken)
                .type("PASSWORD_RESET")
                .expiryDate(LocalDateTime.now().plusHours(1))
                .used(false)
                .createdAt(LocalDateTime.now())
                .build();

        tokenRepository.save(token);

        emailService.sendPasswordResetEmail(user.getEmail(), resetToken);

        log.info("Password reset email sent to: {}", request.getEmail());

        return new MessageResponse("Password reset link sent to your email");
    }

    @Override
    @Transactional
    public MessageResponse resetPassword(ResetPasswordRequest request) {
        log.info("Password reset attempt with token: {}", request.getToken());

        VerificationToken token = tokenRepository
                .findByTokenAndTypeAndUsedFalseAndExpiryDateAfter(
                        request.getToken(),
                        "PASSWORD_RESET",
                        LocalDateTime.now()
                )
                .orElseThrow(() -> new RuntimeException("Invalid or expired reset token"));

        User user = userRepository.findById(token.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);

        token.setUsed(true);
        tokenRepository.save(token);

        refreshTokenRepository.deleteByUserId(user.getId());

        log.info("Password reset successful for user: {}", user.getId());

        return new MessageResponse("Password reset successful! Please login with new password.");
    }

    @Override
    public AuthResponse refreshToken(RefreshTokenRequest request) {
        log.info("Refresh token request");

        RefreshToken refreshToken = refreshTokenRepository
                .findByToken(request.getRefreshToken())
                .orElseThrow(() -> new RuntimeException("Invalid refresh token"));

        if (refreshToken.isExpired()) {
            throw new RuntimeException("Refresh token expired");
        }

        if (refreshToken.getRevoked()) {
            throw new RuntimeException("Refresh token revoked");
        }

        User user = userRepository.findById(refreshToken.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String newAccessToken = jwtTokenProvider.generateTokenFromUsername(user.getEmail());

        log.info("New access token generated for user: {}", user.getId());

        return AuthResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(request.getRefreshToken())
                .tokenType("Bearer")
                .userId(user.getId())
                .email(user.getEmail())
                .roles(user.getRoles())
                .status(user.getStatus())
                .build();
    }

    @Override
    @Transactional
    public MessageResponse logout(String userId) {
        log.info("Logout request for user: {}", userId);

        refreshTokenRepository.deleteByUserId(userId);

        return new MessageResponse("Logout successful");
    }

    @Override
    public MessageResponse validateToken(String token) {
        boolean isValid = jwtTokenProvider.validateToken(token);

        if (isValid) {
            String username = jwtTokenProvider.getUsernameFromToken(token);
            return new MessageResponse("Valid token for user: " + username);
        }

        return new MessageResponse("Invalid token");
    }
}
