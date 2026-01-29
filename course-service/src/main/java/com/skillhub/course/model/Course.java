package com.skillhub.course.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "courses")
public class Course {

    @Id
    private String id;

    @TextIndexed
    private String title;

    @TextIndexed
    private String description;

    private String instructor;

    @Indexed
    private String category;

    @Indexed
    private List<String> tags = new ArrayList<>();

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

    @Indexed
    private CourseStatus status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public Course(String title, String description, String instructor,
                  String category, CourseLevel level, Integer duration,
                  BigDecimal price) {
        this.title = title;
        this.description = description;
        this.instructor = instructor;
        this.category = category;
        this.level = level;
        this.duration = duration;
        this.price = price;
        this.rating = 0.0;
        this.totalEnrollments = 0;
        this.totalReviews = 0;
        this.viewCount = 0;
        this.status = CourseStatus.DRAFT;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
}