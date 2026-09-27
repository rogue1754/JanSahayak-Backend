package JanSahayak.Hackathon.DTOs;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RankedSchemeDTO {

    private Long id;

    private String name;

    private String ministry;

    private String state;

    private String applicationDeadline;

    private int matchScore;

    private int matchedCriteria;

    private int totalCriteria;
}