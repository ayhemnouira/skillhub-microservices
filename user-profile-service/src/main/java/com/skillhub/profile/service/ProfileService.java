package com.skillhub.profile.service;
import com.skillhub.profile.dto.*;
import com.skillhub.profile.model.UserProfile;
import java.util.List;

public interface ProfileService {

    UserProfile createProfile(String userId, ProfileRequest request);
    UserProfile getProfileByUserId(String userId);
    UserProfile getProfileById(String profileId);
    UserProfile updateProfile(String profileId, ProfileRequest request);
    void deleteProfile(String profileId);

    UserProfile addSkill(String profileId, String skill);
    UserProfile removeSkill(String profileId, String skill);

    UserProfile addExperience(String profileId, ExperienceRequest request);
    UserProfile updateExperience(String profileId, String experienceId, ExperienceRequest request);
    UserProfile deleteExperience(String profileId, String experienceId);

    UserProfile addEducation(String profileId, EducationRequest request);
    UserProfile updateEducation(String profileId, String educationId, EducationRequest request);
    UserProfile deleteEducation(String profileId, String educationId);

    Integer calculateProfileCompleteness(UserProfile profile);

    List<UserProfile> searchBySkills(List<String> skills);
    List<UserProfile> searchByLocation(String location);
}