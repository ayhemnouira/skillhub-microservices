package com.skillhub.profile.controller;

import com.skillhub.profile.dto.*;
import com.skillhub.profile.model.UserProfile;
import com.skillhub.profile.service.ProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/profiles")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class ProfileController {

    private final ProfileService profileService;

    @PostMapping
    public ResponseEntity<UserProfile> createProfile(
            @RequestHeader("X-User-Id") String userId,
            @Valid @RequestBody ProfileRequest request) {

        log.info("Creating profile for userId: {}", userId);

        UserProfile profile = profileService.createProfile(userId, request);

        return ResponseEntity.status(HttpStatus.CREATED).body(profile);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<UserProfile> getProfileByUserId(
            @PathVariable String userId) {

        log.info("Fetching profile for userId: {}", userId);

        UserProfile profile = profileService.getProfileByUserId(userId);
        return ResponseEntity.ok(profile);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserProfile> getProfileById(@PathVariable String id) {
        log.info("Fetching profile: {}", id);

        UserProfile profile = profileService.getProfileById(id);
        return ResponseEntity.ok(profile);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserProfile> updateProfile(
            @PathVariable String id,
            @Valid @RequestBody ProfileRequest request) {

        log.info("Updating profile: {}", id);

        UserProfile updatedProfile = profileService.updateProfile(id, request);
        return ResponseEntity.ok(updatedProfile);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteProfile(@PathVariable String id) {
        log.info("Deleting profile: {}", id);

        profileService.deleteProfile(id);

        return ResponseEntity.ok(Map.of("message", "Profile deleted successfully"));
    }

    @PostMapping("/{id}/skills")
    public ResponseEntity<UserProfile> addSkill(
            @PathVariable String id,
            @RequestBody Map<String, String> payload) {

        String skill = payload.get("skill");
        log.info("Adding skill '{}' to profile: {}", skill, id);

        UserProfile profile = profileService.addSkill(id, skill);
        return ResponseEntity.ok(profile);
    }

    @DeleteMapping("/{id}/skills/{skill}")
    public ResponseEntity<UserProfile> removeSkill(
            @PathVariable String id,
            @PathVariable String skill) {

        log.info("Removing skill '{}' from profile: {}", skill, id);

        UserProfile profile = profileService.removeSkill(id, skill);
        return ResponseEntity.ok(profile);
    }

    @PostMapping("/{id}/experience")
    public ResponseEntity<UserProfile> addExperience(
            @PathVariable String id,
            @Valid @RequestBody ExperienceRequest request) {

        log.info("Adding experience to profile: {}", id);

        UserProfile profile = profileService.addExperience(id, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(profile);
    }

    @PutMapping("/{id}/experience/{experienceId}")
    public ResponseEntity<UserProfile> updateExperience(
            @PathVariable String id,
            @PathVariable String experienceId,
            @Valid @RequestBody ExperienceRequest request) {

        log.info("Updating experience {} in profile: {}", experienceId, id);

        UserProfile profile = profileService.updateExperience(id, experienceId, request);
        return ResponseEntity.ok(profile);
    }

    @DeleteMapping("/{id}/experience/{experienceId}")
    public ResponseEntity<UserProfile> deleteExperience(
            @PathVariable String id,
            @PathVariable String experienceId) {

        log.info("Deleting experience {} from profile: {}", experienceId, id);

        UserProfile profile = profileService.deleteExperience(id, experienceId);
        return ResponseEntity.ok(profile);
    }

    @PostMapping("/{id}/education")
    public ResponseEntity<UserProfile> addEducation(
            @PathVariable String id,
            @Valid @RequestBody EducationRequest request) {

        log.info("Adding education to profile: {}", id);

        UserProfile profile = profileService.addEducation(id, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(profile);
    }

    @PutMapping("/{id}/education/{educationId}")
    public ResponseEntity<UserProfile> updateEducation(
            @PathVariable String id,
            @PathVariable String educationId,
            @Valid @RequestBody EducationRequest request) {

        log.info("Updating education {} in profile: {}", educationId, id);

        UserProfile profile = profileService.updateEducation(id, educationId, request);
        return ResponseEntity.ok(profile);
    }

    @DeleteMapping("/{id}/education/{educationId}")
    public ResponseEntity<UserProfile> deleteEducation(
            @PathVariable String id,
            @PathVariable String educationId) {

        log.info("Deleting education {} from profile: {}", educationId, id);

        UserProfile profile = profileService.deleteEducation(id, educationId);
        return ResponseEntity.ok(profile);
    }

    @GetMapping("/{id}/completion")
    public ResponseEntity<Map<String, Object>> getProfileCompletion(
            @PathVariable String id) {

        log.info("Calculating completion for profile: {}", id);

        UserProfile profile = profileService.getProfileById(id);
        Integer completion = profileService.calculateProfileCompleteness(profile);

        Map<String, Object> response = new HashMap<>();
        response.put("profileId", id);
        response.put("completeness", completion);

        String message;
        if (completion < 50) {
            message = "Keep going! Complete your profile to get better job matches.";
        } else if (completion < 80) {
            message = "Good progress! Add more details to stand out.";
        } else {
            message = "Excellent! Your profile is almost perfect!";
        }
        response.put("message", message);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/search/skills")
    public ResponseEntity<List<UserProfile>> searchBySkills(
            @RequestParam List<String> skills) {

        log.info("Searching profiles by skills: {}", skills);

        List<UserProfile> profiles = profileService.searchBySkills(skills);
        return ResponseEntity.ok(profiles);
    }

    @GetMapping("/search/location")
    public ResponseEntity<List<UserProfile>> searchByLocation(
            @RequestParam String location) {

        log.info("Searching profiles by location: {}", location);

        List<UserProfile> profiles = profileService.searchByLocation(location);
        return ResponseEntity.ok(profiles);
    }
}