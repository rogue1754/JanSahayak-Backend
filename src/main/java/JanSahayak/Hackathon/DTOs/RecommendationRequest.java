package JanSahayak.Hackathon.DTOs;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecommendationRequest {

    private String name;

    private LocalDate dateOfBirth;

    private Long annualIncome;

    private String gender;

    private String state;

    private String city;

    private String occupation;

    private String education;

    private String category;

    private Double marksPercentage;

    private Boolean landOwner;

    private Boolean disability;
}
