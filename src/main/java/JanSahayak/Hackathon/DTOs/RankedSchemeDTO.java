package JanSahayak.Hackathon.DTOs;

import JanSahayak.Hackathon.Entities.Scheme;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RankedSchemeDTO {

    private Scheme scheme;

    private int matchScore;

    private int matchedCriteria;

    private int totalCriteria;
}