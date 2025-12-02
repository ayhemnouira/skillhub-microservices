package com.skillhub.auth.repository;

import com.skillhub.auth.entity.VerificationToken;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface VerificationTokenRepository extends MongoRepository<VerificationToken, String> {

    Optional<VerificationToken> findByUserIdAndType(String userId, String type);

    Optional<VerificationToken> findByTokenAndTypeAndUsedFalseAndExpiryDateAfter(
            String token,
            String type,
            LocalDateTime currentTime
    );

    List<VerificationToken> findByExpiryDateBefore(LocalDateTime date);

    void deleteByUserId(String userId);
}