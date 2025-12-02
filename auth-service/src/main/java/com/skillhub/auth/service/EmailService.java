package com.skillhub.auth.service;


public interface EmailService {
    void sendVerificationEmail(String to, String otp);
    void sendPasswordResetEmail(String to, String resetToken);
    void sendWelcomeEmail(String to);
}