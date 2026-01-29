package com.skillhub.course.controller;

import com.skillhub.course.dto.CourseRequest;
import com.skillhub.course.dto.CourseResponse;
import com.skillhub.course.model.CourseLevel;
import com.skillhub.course.model.CourseStatus;
import com.skillhub.course.service.CourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
@Slf4j
public class CourseController {

    private final CourseService courseService;

    @PostMapping
    public ResponseEntity<CourseResponse> createCourse(@Valid @RequestBody CourseRequest request) {
        log.info("POST /api/courses - Creating course: {}", request.getTitle());
        CourseResponse response = courseService.createCourse(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseResponse> getCourseById(@PathVariable String id) {
        log.info("GET /api/courses/{} - Fetching course", id);
        courseService.incrementViewCount(id);
        CourseResponse response = courseService.getCourseById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Page<CourseResponse>> getAllCourses(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "DESC") String sortDirection) {

        log.info("GET /api/courses - Page: {}, Size: {}", page, size);

        Sort sort = sortDirection.equalsIgnoreCase("ASC")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<CourseResponse> courses = courseService.getAllCourses(pageable);

        return ResponseEntity.ok(courses);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<Page<CourseResponse>> getCoursesByStatus(
            @PathVariable CourseStatus status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        log.info("GET /api/courses/status/{} - Fetching courses", status);
        Pageable pageable = PageRequest.of(page, size);
        Page<CourseResponse> courses = courseService.getCoursesByStatus(status, pageable);

        return ResponseEntity.ok(courses);
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<Page<CourseResponse>> getCoursesByCategory(
            @PathVariable String category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        log.info("GET /api/courses/category/{} - Fetching courses", category);
        Pageable pageable = PageRequest.of(page, size);
        Page<CourseResponse> courses = courseService.getCoursesByCategory(category, pageable);

        return ResponseEntity.ok(courses);
    }

    @GetMapping("/level/{level}")
    public ResponseEntity<Page<CourseResponse>> getCoursesByLevel(
            @PathVariable CourseLevel level,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        log.info("GET /api/courses/level/{} - Fetching courses", level);
        Pageable pageable = PageRequest.of(page, size);
        Page<CourseResponse> courses = courseService.getCoursesByLevel(level, pageable);

        return ResponseEntity.ok(courses);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<CourseResponse>> searchCourses(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        log.info("GET /api/courses/search?keyword={}", keyword);
        Pageable pageable = PageRequest.of(page, size);
        Page<CourseResponse> courses = courseService.searchCourses(keyword, pageable);

        return ResponseEntity.ok(courses);
    }

    @GetMapping("/trending")
    public ResponseEntity<Page<CourseResponse>> getTrendingCourses(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        log.info("GET /api/courses/trending - Fetching trending courses");
        Pageable pageable = PageRequest.of(page, size);
        Page<CourseResponse> courses = courseService.getTrendingCourses(pageable);

        return ResponseEntity.ok(courses);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CourseResponse> updateCourse(
            @PathVariable String id,
            @Valid @RequestBody CourseRequest request) {

        log.info("PUT /api/courses/{} - Updating course", id);
        CourseResponse response = courseService.updateCourse(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable String id) {
        log.info("DELETE /api/courses/{} - Deleting course", id);
        courseService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/publish")
    public ResponseEntity<CourseResponse> publishCourse(@PathVariable String id) {
        log.info("PATCH /api/courses/{}/publish - Publishing course", id);
        CourseResponse response = courseService.publishCourse(id);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/archive")
    public ResponseEntity<CourseResponse> archiveCourse(@PathVariable String id) {
        log.info("PATCH /api/courses/{}/archive - Archiving course", id);
        CourseResponse response = courseService.archiveCourse(id);
        return ResponseEntity.ok(response);
    }
}