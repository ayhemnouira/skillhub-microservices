package com.skillhub.profile.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "user_profiles")
public class UserProfile {

    @Id
    private String id;

    @Indexed(unique = true)
    private String userId;

    private String firstName;
    private String lastName;
    private String title;
    private String bio;
    private String phoneNumber;
    private String location;

    private List<String> skills = new ArrayList<>();

    private List<Experience> experience = new ArrayList<>();
    private List<Education> education = new ArrayList<>();

    private String resumeUrl;
    private String profilePictureUrl;
    private String linkedinUrl;
    private String githubUrl;
    private String portfolioUrl;

    private Integer profileCompleteness;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}