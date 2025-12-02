package com.skillhub.auth.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Override
    public void sendVerificationEmail(String to, String otp) {
        log.info("Sending verification email to: {}", to);

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("noreply@skillhub.com");
            message.setTo(to);
            message.setSubject("Verify Your Email - SkillHub");
            message.setText(String.format(
                    "Welcome to SkillHub!\n\n" +
                            "Your email verification code is: %s\n\n" +
                            "This code expires in 10 minutes.\n\n" +
                            "If you didn't create an account, please ignore this email.\n\n" +
                            "Best regards,\n" +
                            "SkillHub Team",
                    otp
            ));

            mailSender.send(message);
            log.info("Verification email sent successfully to: {}", to);

        } catch (Exception e) {
            log.error("Failed to send verification email to {}: {}", to, e.getMessage());
            throw new RuntimeException("Failed to send email. Please try again later.");
        }
    }

    @Override
    public void sendPasswordResetEmail(String to, String resetToken) {
        log.info("Sending password reset email to: {}", to);

        try {
            String resetLink = "http://localhost:3000/reset-password?token=" + resetToken;

            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("noreply@skillhub.com");
            message.setTo(to);
            message.setSubject("Reset Your Password - SkillHub");
            message.setText(String.format(
                    "Hello,\n\n" +
                            "You requested to reset your password.\n\n" +
                            "Click the link below to reset your password:\n" +
                            "%s\n\n" +
                            "This link expires in 1 hour.\n\n" +
                            "If you didn't request this, please ignore this email.\n\n" +
                            "Best regards,\n" +
                            "SkillHub Team",
                    resetLink
            ));

            mailSender.send(message);
            log.info("Password reset email sent successfully to: {}", to);

        } catch (Exception e) {
            log.error("Failed to send password reset email to {}: {}", to, e.getMessage());
            throw new RuntimeException("Failed to send email. Please try again later.");
        }
    }

    @Override
    public void sendWelcomeEmail(String to) {
        log.info("Sending welcome email to: {}", to);

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("noreply@skillhub.com");
            message.setTo(to);
            message.setSubject("Welcome to SkillHub!");
            message.setText(
                    "Welcome to SkillHub!\n\n" +
                            "Your account has been successfully created.\n\n" +
                            "Start exploring courses and job opportunities today!\n\n" +
                            "Best regards,\n" +
                            "SkillHub Team"
            );

            mailSender.send(message);
            log.info("Welcome email sent successfully to: {}", to);

        } catch (Exception e) {
            log.error("Failed to send welcome email to {}: {}", to, e.getMessage());
        }
    }
}
