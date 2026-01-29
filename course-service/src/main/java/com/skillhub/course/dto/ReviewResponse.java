package com.skillhub.course.dto;

import com.skillhub.course.model.CourseReview;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewResponse {

    private String id;
    private String courseId;
    private String userId;
    private Integer rating;
    private String comment;
    private LocalDateTime createdAt;

    public static ReviewResponse fromEntity(CourseReview review) {
        return new ReviewResponse(
                review.getId(),
                review.getCourseId(),
                review.getUserId(),
                review.getRating(),
                review.getComment(),
                review.getCreatedAt()
        );
    }
}