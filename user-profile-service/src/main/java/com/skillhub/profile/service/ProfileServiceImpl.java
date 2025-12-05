package com.skillhub.profile.service;

import com.skillhub.profile.dto.*;
import com.skillhub.profile.exception.ProfileNotFoundException;
import com.skillhub.profile.model.Education;
import com.skillhub.profile.model.Experience;
import com.skillhub.profile.model.UserProfile;
import com.skillhub.profile.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;

    @Override
    public UserProfile createProfile(String userId, ProfileRequest request) {
        log.info("Creating profile for userId: {}", userId);

        if (profileRepository.existsByUserId(userId)) {
            log.error("Profile already exists for userId: {}", userId);
            throw new RuntimeException("Profile already exists for this user");
        }

        UserProfile profile = new UserProfile();
        profile.setUserId(userId);
        profile.setFirstName(request.getFirstName());
        profile.setLastName(request.getLastName());
        profile.setTitle(request.getTitle());
        profile.setBio(request.getBio());
        profile.setPhoneNumber(request.getPhoneNumber());
        profile.setLocation(request.getLocation());
        profile.setLinkedinUrl(request.getLinkedinUrl());
        profile.setGithubUrl(request.getGithubUrl());
        profile.setPortfolioUrl(request.getPortfolioUrl());

        profile.setCreatedAt(LocalDateTime.now());
        profile.setUpdatedAt(LocalDateTime.now());

        profile.setSkills(new ArrayList<>());
        profile.setExperience(new ArrayList<>());
        profile.setEducation(new ArrayList<>());

        profile.setProfileCompleteness(calculateProfileCompleteness(profile));

        UserProfile savedProfile = profileRepository.save(profile);
        log.info("Profile created successfully with ID: {}", savedProfile.getId());

        return savedProfile;
    }

    @Override
    public UserProfile getProfileByUserId(String userId) {
        log.info("Fetching profile for userId: {}", userId);

        return profileRepository.findByUserId(userId)
                .orElseThrow(() -> new ProfileNotFoundException(
                        "Profile not found for userId: " + userId));
    }

    @Override
    public UserProfile getProfileById(String profileId) {
        log.info("Fetching profile by profileId: {}", profileId);

        return profileRepository.findById(profileId)
                .orElseThrow(() -> new ProfileNotFoundException(
                        "Profile not found with id: " + profileId));
    }

    @Override
    public UserProfile updateProfile(String profileId, ProfileRequest request) {
        log.info("Updating profile: {}", profileId);

        UserProfile profile = getProfileById(profileId);

        profile.setFirstName(request.getFirstName());
        profile.setLastName(request.getLastName());
        profile.setTitle(request.getTitle());
        profile.setBio(request.getBio());
        profile.setPhoneNumber(request.getPhoneNumber());
        profile.setLocation(request.getLocation());
        profile.setLinkedinUrl(request.getLinkedinUrl());
        profile.setGithubUrl(request.getGithubUrl());
        profile.setPortfolioUrl(request.getPortfolioUrl());

        profile.setUpdatedAt(LocalDateTime.now());

        profile.setProfileCompleteness(calculateProfileCompleteness(profile));

        return profileRepository.save(profile);
    }

    @Override
    public void deleteProfile(String profileId) {
        log.info("Deleting profile: {}", profileId);

        if (!profileRepository.existsById(profileId)) {
            throw new ProfileNotFoundException("Profile not found with id: " + profileId);
        }

        profileRepository.deleteById(profileId);
        log.info("Profile deleted successfully");
    }

    @Override
    public UserProfile addSkill(String profileId, String skill) {
        log.info("Adding skill '{}' to profile: {}", skill, profileId);

        UserProfile profile = getProfileById(profileId);

        boolean skillExists = profile.getSkills().stream()
                .anyMatch(s -> s.equalsIgnoreCase(skill));

        if (!skillExists) {
            profile.getSkills().add(skill);
            profile.setUpdatedAt(LocalDateTime.now());
            profile.setProfileCompleteness(calculateProfileCompleteness(profile));
            return profileRepository.save(profile);
        }

        log.debug("Skill '{}' already exists in profile", skill);
        return profile;
    }

    @Override
    public UserProfile removeSkill(String profileId, String skill) {
        log.info("Removing skill '{}' from profile: {}", skill, profileId);

        UserProfile profile = getProfileById(profileId);

        profile.getSkills().removeIf(s -> s.equalsIgnoreCase(skill));
        profile.setUpdatedAt(LocalDateTime.now());
        profile.setProfileCompleteness(calculateProfileCompleteness(profile));

        return profileRepository.save(profile);
    }

    @Override
    public UserProfile addExperience(String profileId, ExperienceRequest request) {
        log.info("Adding experience to profile: {}", profileId);

        UserProfile profile = getProfileById(profileId);

        Experience experience = new Experience();
        experience.setId(UUID.randomUUID().toString());
        experience.setCompany(request.getCompany());
        experience.setPosition(request.getPosition());
        experience.setLocation(request.getLocation());
        experience.setStartDate(request.getStartDate());
        experience.setEndDate(request.getEndDate());
        experience.setIsCurrent(request.getIsCurrent());
        experience.setDescription(request.getDescription());

        profile.getExperience().add(experience);
        profile.setUpdatedAt(LocalDateTime.now());
        profile.setProfileCompleteness(calculateProfileCompleteness(profile));

        return profileRepository.save(profile);
    }

    @Override
    public UserProfile updateExperience(String profileId, String experienceId,
                                        ExperienceRequest request) {
        log.info("Updating experience {} in profile: {}", experienceId, profileId);

        UserProfile profile = getProfileById(profileId);

        Experience experience = profile.getExperience().stream()
                .filter(exp -> exp.getId().equals(experienceId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException(
                        "Experience not found with id: " + experienceId));

        experience.setCompany(request.getCompany());
        experience.setPosition(request.getPosition());
        experience.setLocation(request.getLocation());
        experience.setStartDate(request.getStartDate());
        experience.setEndDate(request.getEndDate());
        experience.setIsCurrent(request.getIsCurrent());
        experience.setDescription(request.getDescription());

        profile.setUpdatedAt(LocalDateTime.now());

        return profileRepository.save(profile);
    }

    @Override
    public UserProfile deleteExperience(String profileId, String experienceId) {
        log.info("Deleting experience {} from profile: {}", experienceId, profileId);

        UserProfile profile = getProfileById(profileId);

        profile.getExperience().removeIf(exp -> exp.getId().equals(experienceId));
        profile.setUpdatedAt(LocalDateTime.now());
        profile.setProfileCompleteness(calculateProfileCompleteness(profile));

        return profileRepository.save(profile);
    }

    @Override
    public UserProfile addEducation(String profileId, EducationRequest request) {
        log.info("Adding education to profile: {}", profileId);

        UserProfile profile = getProfileById(profileId);

        Education education = new Education();
        education.setId(UUID.randomUUID().toString());
        education.setInstitution(request.getInstitution());
        education.setDegree(request.getDegree());
        education.setFieldOfStudy(request.getFieldOfStudy());
        education.setStartDate(request.getStartDate());
        education.setEndDate(request.getEndDate());
        education.setGrade(request.getGrade());

        profile.getEducation().add(education);
        profile.setUpdatedAt(LocalDateTime.now());
        profile.setProfileCompleteness(calculateProfileCompleteness(profile));

        return profileRepository.save(profile);
    }

    @Override
    public UserProfile updateEducation(String profileId, String educationId,
                                       EducationRequest request) {
        log.info("Updating education {} in profile: {}", educationId, profileId);

        UserProfile profile = getProfileById(profileId);

        Education education = profile.getEducation().stream()
                .filter(edu -> edu.getId().equals(educationId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException(
                        "Education not found with id: " + educationId));

        education.setInstitution(request.getInstitution());
        education.setDegree(request.getDegree());
        education.setFieldOfStudy(request.getFieldOfStudy());
        education.setStartDate(request.getStartDate());
        education.setEndDate(request.getEndDate());
        education.setGrade(request.getGrade());

        profile.setUpdatedAt(LocalDateTime.now());

        return profileRepository.save(profile);
    }

    @Override
    public UserProfile deleteEducation(String profileId, String educationId) {
        log.info("Deleting education {} from profile: {}", educationId, profileId);

        UserProfile profile = getProfileById(profileId);

        profile.getEducation().removeIf(edu -> edu.getId().equals(educationId));
        profile.setUpdatedAt(LocalDateTime.now());
        profile.setProfileCompleteness(calculateProfileCompleteness(profile));

        return profileRepository.save(profile);
    }

    @Override
    public Integer calculateProfileCompleteness(UserProfile profile) {
        int score = 0;

        if (profile.getFirstName() != null && !profile.getFirstName().isEmpty()) {
            score += 5;
        }
        if (profile.getLastName() != null && !profile.getLastName().isEmpty()) {
            score += 5;
        }
        if (profile.getTitle() != null && !profile.getTitle().isEmpty()) {
            score += 10;
        }
        if (profile.getBio() != null && profile.getBio().length() > 50) {
            score += 10;
        }
        if (profile.getPhoneNumber() != null && !profile.getPhoneNumber().isEmpty()) {
            score += 5;
        }
        if (profile.getLocation() != null && !profile.getLocation().isEmpty()) {
            score += 5;
        }
        if (profile.getProfilePictureUrl() != null) {
            score += 10;
        }

        if (profile.getSkills() != null && profile.getSkills().size() >= 5) {
            score += 15;
        } else if (profile.getSkills() != null && profile.getSkills().size() >= 3) {
            score += 10;
        } else if (profile.getSkills() != null && profile.getSkills().size() >= 1) {
            score += 5;
        }

        if (profile.getExperience() != null && !profile.getExperience().isEmpty()) {
            score += 15;
        }

        if (profile.getEducation() != null && !profile.getEducation().isEmpty()) {
            score += 10;
        }

        if (profile.getResumeUrl() != null && !profile.getResumeUrl().isEmpty()) {
            score += 10;
        }

        return Math.min(score, 100);
    }

    @Override
    public List<UserProfile> searchBySkills(List<String> skills) {
        log.info("Searching profiles by skills: {}", skills);
        return profileRepository.findBySkillsIn(skills);
    }

    @Override
    public List<UserProfile> searchByLocation(String location) {
        log.info("Searching profiles by location: {}", location);
        return profileRepository.findByLocationContainingIgnoreCase(location);
    }
}