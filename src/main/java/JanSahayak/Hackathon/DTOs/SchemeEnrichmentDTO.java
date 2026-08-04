package JanSahayak.Hackathon.DTOs;

import JanSahayak.Hackathon.Enums.SchemeCategory;
import JanSahayak.Hackathon.Enums.SchemeLevel;
import JanSahayak.Hackathon.Enums.SourceType;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SchemeEnrichmentDTO {

    // Used to find existing scheme
    private String schemeName;

    // Scheme metadata
    private String description;
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

    // Eligibility + documents
    private List<ExtractedEligibilityRule> eligibilityRules;

    private List<DocumentSeedDTO> documents;
}