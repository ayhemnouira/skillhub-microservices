package com.skillhub.course.repository;

import com.skillhub.course.model.CourseReview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends MongoRepository<CourseReview, String> {

    Page<CourseReview> findByCourseId(String courseId, Pageable pageable);

    List<CourseReview> findByCourseId(String courseId);

    Optional<CourseReview> findByCourseIdAndUserId(String courseId, String userId);

    boolean existsByCourseIdAndUserId(String courseId, String userId);
}