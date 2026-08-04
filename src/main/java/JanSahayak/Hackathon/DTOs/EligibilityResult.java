package JanSahayak.Hackathon.DTOs;
import JanSahayak.Hackathon.Enums.EligibilityField;
import JanSahayak.Hackathon.Enums.EligibilityStatus;
import lombok.*;

import java.util.List;

@Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public class EligibilityResult {

        private Long schemeId;

        private String schemeName;

        private EligibilityStatus status;

        private List<String> matchedRules;

        private List<String> failedRules;

        private List<EligibilityField> missingFields;

}
