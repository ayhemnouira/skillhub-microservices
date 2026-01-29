package com.skillhub.course.dto;

import com.skillhub.course.model.Course;
import com.skillhub.course.model.CourseLevel;
import com.skillhub.course.model.CourseStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseResponse {

    private String id;
    private String title;
    private String description;
    private String instructor;
    private String category;
    private List<String> tags;
    private CourseLevel level;
    private Integer duration;
    private BigDecimal price;
    private BigDecimal discountPrice;
    private String thumbnailUrl;
    private String videoUrl;
    private Double rating;
    private Integer totalEnrollments;
    private Integer totalReviews;
    private Integer viewCount;
    private CourseStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static CourseResponse fromEntity(Course course) {
        return new CourseResponse(
                course.getId(),
                course.getTitle(),
                course.getDescription(),
                course.getInstructor(),
                course.getCategory(),
                course.getTags(),
                course.getLevel(),
                course.getDuration(),
                course.getPrice(),
                course.getDiscountPrice(),
                course.getThumbnailUrl(),
                course.getVideoUrl(),
                course.getRating(),
                course.getTotalEnrollments(),
                course.getTotalReviews(),
                course.getViewCount(),
                course.getStatus(),
                course.getCreatedAt(),
                course.getUpdatedAt()
        );
    }
}