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
public class CreateSchemeRequest {

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

    private List<EligibilityRuleRequest> eligibilityRules;

    private List<RequiredDocumentRequest> requiredDocuments;
}