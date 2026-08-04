package JanSahayak.Hackathon.DTOs;

import JanSahayak.Hackathon.Enums.EligibilityField;
import JanSahayak.Hackathon.Enums.RuleOperator;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExtractedEligibilityRule {

    private EligibilityField field;
    private RuleOperator operator;
    private String expectedValue;

    private Boolean mandatory;
    private String rawRule;
}