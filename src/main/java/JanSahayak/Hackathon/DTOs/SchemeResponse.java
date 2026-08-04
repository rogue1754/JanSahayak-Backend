package JanSahayak.Hackathon.DTOs;

import JanSahayak.Hackathon.Enums.SchemeCategory;
import JanSahayak.Hackathon.Enums.SchemeLevel;
import JanSahayak.Hackathon.Enums.SourceType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.*;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SchemeResponse {

    private Long id;

    private String name;

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

    private boolean active;

    private LocalDateTime lastUpdated;

    private List<EligibilityRuleResponse> eligibilityRules;

    private List<RequiredDocumentResponse> requiredDocuments;
}
