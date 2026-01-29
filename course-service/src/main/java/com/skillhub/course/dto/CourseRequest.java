package com.skillhub.course.dto;

import com.skillhub.course.model.CourseLevel;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseRequest {

    @NotBlank(message = "Title is required")
    @Size(min = 5, max = 200, message = "Title must be between 5 and 200 characters")
    private String title;

    @NotBlank(message = "Description is required")
    @Size(min = 20, max = 5000, message = "Description must be between 20 and 5000 characters")
    private String description;

    @NotBlank(message = "Instructor name is required")
    private String instructor;

    @NotBlank(message = "Category is required")
    private String category;

    private List<String> tags;

    @NotNull(message = "Course level is required")
    private CourseLevel level;

    @NotNull(message = "Duration is required")
    @Min(value = 1, message = "Duration must be at least 1 hour")
    private Integer duration;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "Price must be positive")
    private BigDecimal price;

    @DecimalMin(value = "0.0", inclusive = true, message = "Discount price must be positive")
    private BigDecimal discountPrice;

    private String thumbnailUrl;

    private String videoUrl;
}