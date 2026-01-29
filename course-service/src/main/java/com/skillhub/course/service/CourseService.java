package com.skillhub.course.service;

import com.skillhub.course.dto.*;
import com.skillhub.course.model.CourseLevel;
import com.skillhub.course.model.CourseStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CourseService {

    CourseResponse createCourse(CourseRequest request);

    CourseResponse getCourseById(String id);

    Page<CourseResponse> getAllCourses(Pageable pageable);

    Page<CourseResponse> getCoursesByStatus(CourseStatus status, Pageable pageable);

    Page<CourseResponse> getCoursesByCategory(String category, Pageable pageable);

    Page<CourseResponse> getCoursesByLevel(CourseLevel level, Pageable pageable);

    Page<CourseResponse> searchCourses(String keyword, Pageable pageable);

    CourseResponse updateCourse(String id, CourseRequest request);

    void deleteCourse(String id);

    CourseResponse publishCourse(String id);

    CourseResponse archiveCourse(String id);

    void incrementViewCount(String id);

    void incrementEnrollmentCount(String id);

    Page<CourseResponse> getTrendingCourses(Pageable pageable);

    ReviewResponse addReview(String courseId, String userId, ReviewRequest request);

    Page<ReviewResponse> getCourseReviews(String courseId, Pageable pageable);
}