package com.skillhub.course.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "course_reviews")
public class CourseReview {

    @Id
    private String id;

    @Indexed
    private String courseId;

    @Indexed
    private String userId;

    private Integer rating;

    private String comment;

    private LocalDateTime createdAt;

    public CourseReview(String courseId, String userId, Integer rating, String comment) {
        this.courseId = courseId;
        this.userId = userId;
        this.rating = rating;
        this.comment = comment;
        this.createdAt = LocalDateTime.now();
    }
}