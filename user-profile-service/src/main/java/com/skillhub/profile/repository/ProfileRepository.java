package com.skillhub.profile.repository;
import com.skillhub.profile.model.UserProfile;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProfileRepository extends MongoRepository<UserProfile, String> {

    Optional<UserProfile> findByUserId(String userId);

    boolean existsByUserId(String userId);

    @Query("{ 'skills': { $in: ?0 } }")
    List<UserProfile> findBySkillsIn(List<String> skills);

    List<UserProfile> findByLocationContainingIgnoreCase(String location);

    List<UserProfile> findByProfileCompletenessGreaterThanEqual(Integer completeness);
}