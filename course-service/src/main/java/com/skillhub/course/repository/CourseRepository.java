package com.skillhub.course.repository;

import com.skillhub.course.model.Course;
import com.skillhub.course.model.CourseLevel;
import com.skillhub.course.model.CourseStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends MongoRepository<Course, String> {

    Page<Course> findByStatus(CourseStatus status, Pageable pageable);

    Page<Course> findByCategory(String category, Pageable pageable);

    Page<Course> findByLevel(CourseLevel level, Pageable pageable);

    Page<Course> findByTagsIn(List<String> tags, Pageable pageable);

    @Query("{ 'title': { $regex: ?0, $options: 'i' } }")
    Page<Course> searchByTitle(String keyword, Pageable pageable);

    @Query("{ $or: [ { 'title': { $regex: ?0, $options: 'i' } }, { 'description': { $regex: ?0, $options: 'i' } } ] }")
    Page<Course> searchByTitleOrDescription(String keyword, Pageable pageable);

    List<Course> findTop10ByStatusOrderByTotalEnrollmentsDesc(CourseStatus status);
}