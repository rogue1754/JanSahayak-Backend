package JanSahayak.Hackathon.DTOs;

import JanSahayak.Hackathon.Enums.SchemeCategory;
import JanSahayak.Hackathon.Enums.SchemeLevel;
import JanSahayak.Hackathon.Enums.SourceType;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SchemeSeedDTO {

    private String name;
    private String description;
    private String slug;
    private String benefits;

    private SchemeCategory category;

    private String ministry;

    private SchemeLevel schemeLevel;
    private String state;

    private String applicationUrl;
    private String applicationDeadline;

    private String sourceUrl;
    private SourceType sourceType;

    private Boolean active;

    private String eligibilityText;
    private List<ExtractedEligibilityRule> eligibilityRules;

    private List<DocumentSeedDTO> documents;
}