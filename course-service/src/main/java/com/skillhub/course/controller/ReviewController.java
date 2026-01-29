package com.skillhub.course.controller;

import com.skillhub.course.dto.ReviewRequest;
import com.skillhub.course.dto.ReviewResponse;
import com.skillhub.course.service.CourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/courses/{courseId}/reviews")
@RequiredArgsConstructor
@Slf4j
public class ReviewController {

    private final CourseService courseService;

    @PostMapping
    public ResponseEntity<ReviewResponse> addReview(
            @PathVariable String courseId,
            @RequestHeader("X-User-Id") String userId,
            @Valid @RequestBody ReviewRequest request) {

        log.info("POST /api/courses/{}/reviews - User: {}", courseId, userId);
        ReviewResponse response = courseService.addReview(courseId, userId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<Page<ReviewResponse>> getCourseReviews(
            @PathVariable String courseId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        log.info("GET /api/courses/{}/reviews - Page: {}, Size: {}", courseId, page, size);
        Pageable pageable = PageRequest.of(page, size);
        Page<ReviewResponse> reviews = courseService.getCourseReviews(courseId, pageable);

        return ResponseEntity.ok(reviews);
    }
}