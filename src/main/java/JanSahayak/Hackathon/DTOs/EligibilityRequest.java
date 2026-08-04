package JanSahayak.Hackathon.DTOs;

import JanSahayak.Hackathon.Enums.Category;
import JanSahayak.Hackathon.Enums.EducationLevel;
import JanSahayak.Hackathon.Enums.EmploymentStatus;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EligibilityRequest {

    // Accepted but redundant
    private String fullName;

    // Personal
    private Integer age;
    private String gender;

    // Location
    private String state;
    private String city;

    // Accepted but redundant
    private String completeAddress;
    private String occupation;
    private LocalDate dob;

    // Social
    private Category category;
    private Boolean minority;
    private Boolean disabled;
    private Boolean student;

    // Education / Employment
    private EmploymentStatus employmentStatus;
    private EducationLevel highestEducation;

    // Financial
    private Long annualIncome;
}