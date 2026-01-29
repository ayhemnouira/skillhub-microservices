package com.skillhub.course.service;

import com.skillhub.course.dto.*;
import com.skillhub.course.exception.CourseNotFoundException;
import com.skillhub.course.model.*;
import com.skillhub.course.repository.CourseRepository;
import com.skillhub.course.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final ReviewRepository reviewRepository;

    @Override
    @Transactional
    public CourseResponse createCourse(CourseRequest request) {
        log.info("Creating new course: {}", request.getTitle());

        Course course = new Course();
        course.setTitle(request.getTitle());
        course.setDescription(request.getDescription());
        course.setInstructor(request.getInstructor());
        course.setCategory(request.getCategory());
        course.setTags(request.getTags());
        course.setLevel(request.getLevel());
        course.setDuration(request.getDuration());
        course.setPrice(request.getPrice());
        course.setDiscountPrice(request.getDiscountPrice());
        course.setThumbnailUrl(request.getThumbnailUrl());
        course.setVideoUrl(request.getVideoUrl());
        course.setRating(0.0);
        course.setTotalEnrollments(0);
        course.setTotalReviews(0);
        course.setViewCount(0);
        course.setStatus(CourseStatus.DRAFT);
        course.setCreatedAt(LocalDateTime.now());
        course.setUpdatedAt(LocalDateTime.now());

        Course savedCourse = courseRepository.save(course);
        log.info("Course created successfully with ID: {}", savedCourse.getId());

        return CourseResponse.fromEntity(savedCourse);
    }

    @Override
    public CourseResponse getCourseById(String id) {
        log.info("Fetching course by ID: {}", id);

        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new CourseNotFoundException("Course not found with ID: " + id));

        return CourseResponse.fromEntity(course);
    }

    @Override
    public Page<CourseResponse> getAllCourses(Pageable pageable) {
        log.info("Fetching all courses - Page: {}, Size: {}", pageable.getPageNumber(), pageable.getPageSize());

        return courseRepository.findAll(pageable)
                .map(CourseResponse::fromEntity);
    }

    @Override
    public Page<CourseResponse> getCoursesByStatus(CourseStatus status, Pageable pageable) {
        log.info("Fetching courses by status: {}", status);

        return courseRepository.findByStatus(status, pageable)
                .map(CourseResponse::fromEntity);
    }

    @Override
    public Page<CourseResponse> getCoursesByCategory(String category, Pageable pageable) {
        log.info("Fetching courses by category: {}", category);

        return courseRepository.findByCategory(category, pageable)
                .map(CourseResponse::fromEntity);
    }

    @Override
    public Page<CourseResponse> getCoursesByLevel(CourseLevel level, Pageable pageable) {
        log.info("Fetching courses by level: {}", level);

        return courseRepository.findByLevel(level, pageable)
                .map(CourseResponse::fromEntity);
    }

    @Override
    public Page<CourseResponse> searchCourses(String keyword, Pageable pageable) {
        log.info("Searching courses with keyword: {}", keyword);

        return courseRepository.searchByTitleOrDescription(keyword, pageable)
                .map(CourseResponse::fromEntity);
    }

    @Override
    @Transactional
    public CourseResponse updateCourse(String id, CourseRequest request) {
        log.info("Updating course with ID: {}", id);

        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new CourseNotFoundException("Course not found with ID: " + id));

        course.setTitle(request.getTitle());
        course.setDescription(request.getDescription());
        course.setInstructor(request.getInstructor());
        course.setCategory(request.getCategory());
        course.setTags(request.getTags());
        course.setLevel(request.getLevel());
        course.setDuration(request.getDuration());
        course.setPrice(request.getPrice());
        course.setDiscountPrice(request.getDiscountPrice());
        course.setThumbnailUrl(request.getThumbnailUrl());
        course.setVideoUrl(request.getVideoUrl());
        course.setUpdatedAt(LocalDateTime.now());

        Course updatedCourse = courseRepository.save(course);
        log.info("Course updated successfully: {}", updatedCourse.getId());

        return CourseResponse.fromEntity(updatedCourse);
    }

    @Override
    @Transactional
    public void deleteCourse(String id) {
        log.info("Deleting course with ID: {}", id);

        if (!courseRepository.existsById(id)) {
            throw new CourseNotFoundException("Course not found with ID: " + id);
        }

        courseRepository.deleteById(id);
        log.info("Course deleted successfully: {}", id);
    }

    @Override
    @Transactional
    public CourseResponse publishCourse(String id) {
        log.info("Publishing course with ID: {}", id);

        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new CourseNotFoundException("Course not found with ID: " + id));

        course.setStatus(CourseStatus.PUBLISHED);
        course.setUpdatedAt(LocalDateTime.now());

        Course publishedCourse = courseRepository.save(course);
        log.info("Course published successfully: {}", publishedCourse.getId());

        return CourseResponse.fromEntity(publishedCourse);
    }

    @Override
    @Transactional
    public CourseResponse archiveCourse(String id) {
        log.info("Archiving course with ID: {}", id);

        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new CourseNotFoundException("Course not found with ID: " + id));

        course.setStatus(CourseStatus.ARCHIVED);
        course.setUpdatedAt(LocalDateTime.now());

        Course archivedCourse = courseRepository.save(course);
        log.info("Course archived successfully: {}", archivedCourse.getId());

        return CourseResponse.fromEntity(archivedCourse);
    }

    @Override
    @Transactional
    public void incrementViewCount(String id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new CourseNotFoundException("Course not found with ID: " + id));

        course.setViewCount(course.getViewCount() + 1);
        courseRepository.save(course);
    }

    @Override
    @Transactional
    public void incrementEnrollmentCount(String id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new CourseNotFoundException("Course not found with ID: " + id));

        course.setTotalEnrollments(course.getTotalEnrollments() + 1);
        courseRepository.save(course);
    }

    @Override
    public Page<CourseResponse> getTrendingCourses(Pageable pageable) {
        log.info("Fetching trending courses");

        List<Course> trendingCourses = courseRepository
                .findTop10ByStatusOrderByTotalEnrollmentsDesc(CourseStatus.PUBLISHED);

        return courseRepository.findAll(
                PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "totalEnrollments"))
        ).map(CourseResponse::fromEntity);
    }

    @Override
    @Transactional
    public ReviewResponse addReview(String courseId, String userId, ReviewRequest request) {
        log.info("Adding review for course: {} by user: {}", courseId, userId);

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseNotFoundException("Course not found with ID: " + courseId));

        if (reviewRepository.existsByCourseIdAndUserId(courseId, userId)) {
            throw new IllegalStateException("User has already reviewed this course");
        }

        CourseReview review = new CourseReview(courseId, userId, request.getRating(), request.getComment());
        CourseReview savedReview = reviewRepository.save(review);

        updateCourseRating(courseId);

        log.info("Review added successfully: {}", savedReview.getId());
        return ReviewResponse.fromEntity(savedReview);
    }

    @Override
    public Page<ReviewResponse> getCourseReviews(String courseId, Pageable pageable) {
        log.info("Fetching reviews for course: {}", courseId);

        return reviewRepository.findByCourseId(courseId, pageable)
                .map(ReviewResponse::fromEntity);
    }

    private void updateCourseRating(String courseId) {
        List<CourseReview> reviews = reviewRepository.findByCourseId(courseId);

        if (reviews.isEmpty()) {
            return;
        }

        double averageRating = reviews.stream()
                .mapToInt(CourseReview::getRating)
                .average()
                .orElse(0.0);

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseNotFoundException("Course not found with ID: " + courseId));

        course.setRating(Math.round(averageRating * 10.0) / 10.0);
        course.setTotalReviews(reviews.size());
        courseRepository.save(course);
    }
}