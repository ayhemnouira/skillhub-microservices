package com.skillhub.profile.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Experience {

    private String id;
    private String company;
    private String position;
    private String location;
    private LocalDate startDate;
    private LocalDate endDate;
    private Boolean isCurrent;
    private String description;

    public Experience(String company, String position, String location,
                      LocalDate startDate, LocalDate endDate,
                      Boolean isCurrent, String description) {
        this.id = UUID.randomUUID().toString();
        this.company = company;
        this.position = position;
        this.location = location;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isCurrent = isCurrent;
        this.description = description;
    }
}