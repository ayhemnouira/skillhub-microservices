package com.skillhub.profile.dto;
import lombok.Data;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.URL;

@Data
public class ProfileRequest {

    @NotBlank(message = "First name is required")
    @Size(min = 2, max = 50, message = "First name must be between 2-50 characters")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(min = 2, max = 50, message = "Last name must be between 2-50 characters")
    private String lastName;

    @Size(max = 100, message = "Title cannot exceed 100 characters")
    private String title;

    @Size(max = 500, message = "Bio cannot exceed 500 characters")
    private String bio;

    @Pattern(regexp = "^\\+?[0-9]{8,15}$", message = "Invalid phone number format")
    private String phoneNumber;

    @Size(max = 100, message = "Location cannot exceed 100 characters")
    private String location;

    @Pattern(regexp = "^(https?://)?(www\\.)?linkedin\\.com/.*$",
            message = "Invalid LinkedIn URL")
    private String linkedinUrl;

    @Pattern(regexp = "^(https?://)?(www\\.)?github\\.com/.*$",
            message = "Invalid GitHub URL")
    private String githubUrl;

    @URL(message = "Invalid portfolio URL")
    private String portfolioUrl;
}